import jenkins.model.Jenkins
import hudson.slaves.EnvironmentVariablesNodeProperty
import hudson.slaves.WorkspaceList
import org.csanchez.jenkins.plugins.kubernetes.KubernetesCloud

instance = Jenkins.getInstance()

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
envVars.put("NO_PROXY", "nexus,localhost,127.0.0.1")
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
println("Creating Job \"DependencyCheck DataUpdate\"")
instance.createProjectFromXML("DependencyCheck DataUpdate", new FileInputStream(new File("/var/lib/jenkins/jobTemplates/dependency_check_data_update_config.xml")))