import hudson.slaves.EnvironmentVariablesNodeProperty
import hudson.slaves.WorkspaceList
import jenkins.model.Jenkins
import net.sf.json.JSONArray
import net.sf.json.JSONObject
import org.csanchez.jenkins.plugins.kubernetes.KubernetesCloud
import org.thoughtslive.jenkins.plugins.jira.Config
import org.thoughtslive.jenkins.plugins.jira.Site

instance = Jenkins.getInstance()

def env = System.getenv()

def qqUserName = env["QQ_USER_NAME"]?.trim()
def qqUserPassword = env["QQ_USER_PASSWORD"]?.trim()

// Set quiet period to 270 sec
println("Setting quiet period..")
instance.setQuietPeriod(270)

// Add "NO_PROXY" environment variable
println("Adding \"NO_PROXY\" environment variable..")
globalNodeProperties = instance.getGlobalNodeProperties()
envVarsNodePropertyList = globalNodeProperties.getAll(EnvironmentVariablesNodeProperty.class)
newEnvVarsNodeProperty = null
envVars = null
if (envVarsNodePropertyList == null || envVarsNodePropertyList.size() == 0) {
    newEnvVarsNodeProperty = new EnvironmentVariablesNodeProperty();
    globalNodeProperties.add(newEnvVarsNodeProperty)
    envVars = newEnvVarsNodeProperty.getEnvVars()
} else {
    envVars = envVarsNodePropertyList.get(0).getEnvVars()
}
envVars.put("NO_PROXY", "*.bmwgroup.net,bmwgroup.net,localhost,127.0.0.1")
instance.save()


// Set "hudson.slaves.WorkspaceList" to "_" to avoid confusion in concurrent builds
println("Setting \"hudson.slaves.WorkspaceList\" to \"_\"..")
System.setProperty(WorkspaceList.class.getName(), "_")


// Set ContainerCap in Kubernetes Plugin
println("Setting Kubernetes container cap..")
if(instance.clouds != null) {
	KubernetesCloud cloud = instance.clouds[0]
	if(cloud != null) {
		cloud.setContainerCapStr("")
		println("Kubernetes container cap set")
	} else {
		println("Kubernetes cloud not available!")
	}
} else {
	println("Kubernetes clouds not available!")
}


// TODO Configure Xray Plugin
println("TODO: Configure XRay Plugin!")

// Create Jobs
println("Creating Job \"DependencyCheck_DataUpdate\"")
try {
	instance.createProjectFromXML("DependencyCheck_DataUpdate", new FileInputStream(new File("/var/lib/jenkins/jobTemplates/dependency_check_data_update_config.xml")))
} catch(IllegalArgumentException e) {
	println("Job \"DependencyCheck_DataUpdate\" already exists")
}

// Configure Stash (Bitbucket Server) Notifier
println("Configuring stashNotifier (Bitbucket Server)...")

def bitbucketServerRootUrl = env["BITBUCKET_ROOT_URL"]?.trim()

if (!bitbucketServerRootUrl) {
    println("Missing Bitbucket root URL. Skipping...")
} else {
    def stashNotifier = Jenkins.instance.getExtensionList(org.jenkinsci.plugins.stashNotifier.StashNotifier.DescriptorImpl.class)[0]

    // Config for v1.13
    // https://github.com/jenkinsci/stashnotifier-plugin/blob/4b2b3fd97b534569e21e1a4bccf8c977a9d7e4da/src/main/java/org/jenkinsci/plugins/stashNotifier/StashNotifier.java#L678

    JSONObject formData = new JSONObject()

    formData.put("stashRootUrl", bitbucketServerRootUrl)
    formData.put("ignoreUnverifiedSsl", false)
    formData.put("includeBuildNumberInKey", true)
    // Configured in 1_configure-global-credentials.groovy
    formData.put("credentialsId", "qq-user")
    formData.put("prependParentProjectKey", false)
    formData.put("disableInprogressNotification", false)
    formData.put("considerUnstableAsSuccess", false)

    stashNotifier.configure(null, formData)
    stashNotifier.save()
}

// Configure JIRA steps

println("Configuring jira-steps...")

if (!qqUserName || !qqUserPassword) {
    println("Missing QQ user. Skipping...")
} else {
    // https://jenkinsci.github.io/jira-steps-plugin/getting-started/config/script/

    JSONArray sites = [
            [
                    name       : 'ATC JIRA',
                    url        : 'https://atc.bmwgroup.net/jira/',
                    timeout    : 10000,
                    readTimeout: 10000,
                    loginType  : 'BASIC',
                    userName   : qqUserName,
                    password   : qqUserPassword
            ]
    ] as JSONArray

    //get global Jenkins configuration
    Config.ConfigDescriptorImpl config = Jenkins.instance.getExtensionList(Config.ConfigDescriptorImpl.class)[0]

    //delete all existing sites
    config.@sites.clear()

    //configure new sites from the above JSONArray
    sites.each { s ->
        String loginType = s.optString('loginType', '').toUpperCase()
        if (loginType in ['BASIC', 'OAUTH']) {
            Site site = new Site(s.optString('name', ''), new URL(s.optString('url', '')), s.optString('loginType', ''), s.optInt('timeout', 10000))
            if (loginType == 'BASIC') {
                site.setUserName(s.optString('userName', ''))
                site.setPassword(s.optString('password', ''))
                site.setReadTimeout(s.optInt('readTimeout', 10000))
            } else { //loginType is OAUTH
                site.setConsumerKey(s.optString('consumerKey', ''))
                site.setPrivateKey(s.optString('privateKey', ''))
                site.setSecret(s.optString('secret', ''))
                site.setToken(s.optString('token', ''))
                site.setReadTimeout(s.optInt('readTimeout', 10000))
            }
            //setSites does not make sense as a name because you can only set one site instead of a list :-/
            config.setSites(site)
        }
    }

    //persist configuration to disk as XML
    config.save()
}