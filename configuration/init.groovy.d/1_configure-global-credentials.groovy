import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*
import hudson.plugins.sshslaves.*
import hudson.util.Secret
import jenkins.model.*
import org.apache.commons.fileupload.*
import org.apache.commons.fileupload.disk.*
import org.jenkinsci.plugins.plaincredentials.*
import org.jenkinsci.plugins.plaincredentials.impl.*


domain = Domain.global()
store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

// Get credentials from environment
def env = System.getenv()
String mavenDeploymentTokenBase64 = env["MAVEN_DEPLOYMENT_TOKEN_FROM_SECRET"]
String mavenDeploymentToken = new String(mavenDeploymentTokenBase64.decodeBase64())

// Set proxy
if (env["USE_PROXY"] != null && env["USE_PROXY"] == "true") {
    String proxyUserBase64 = env["PROXY_USER_FROM_SECRET"]
    String proxyPassBase64 = env["PROXY_PASS_FROM_SECRET"]
    String proxyUser = new String(proxyUserBase64.decodeBase64())
    String proxyPass = new String(proxyPassBase64.decodeBase64())
    // "http-proxy" for npm builds
    println("Creating \"http-proxy\" secret text..")
    store.addCredentials(domain, new StringCredentialsImpl(
            CredentialsScope.GLOBAL,
            "http-proxy",
            "http-proxy",
            Secret.fromString("http://" + proxyUser + ":" + proxyPass + "@proxy.muc:8080"))
    )

    // "https-proxy" for npm builds
    println("Creating \"https-proxy\" secret text..")
    store.addCredentials(domain, new StringCredentialsImpl(
            CredentialsScope.GLOBAL,
            "https-proxy",
            "https-proxy",
            Secret.fromString("http://" + proxyUser + ":" + proxyPass + "@proxy.muc:8080"))
    )
}

// "maven-deployment-token" for Maven builds
println("Creating \"maven-deployment-token\" secret text..")
store.addCredentials(domain, new StringCredentialsImpl(
        CredentialsScope.GLOBAL,
        "maven-deployment-token",
        "maven-deployment-token",
        Secret.fromString(mavenDeploymentToken))
)