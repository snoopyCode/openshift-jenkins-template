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


def env = System.getenv()
String npmRepoTokenBase64 = env["NEXUS_NPM_REPO_TOKEN_FROM_SECRET"]
String mavenDeploymentTokenBase64 = env["MAVEN_DEPLOYMENT_TOKEN_FROM_SECRET"]
String npmRepoToken = new String(npmRepoTokenBase64.decodeBase64())
String mavenDeploymentToken = new String(mavenDeploymentTokenBase64.decodeBase64())

domain = Domain.global()
store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

//TODO should be set by-default using the openshift jenkins image
// "pps-ea-ppsea-ssh" for OpenShift integration
//store.addCredentials(domain, new BasicSSHUserPrivateKey(
//        CredentialsScope.GLOBAL,
//        "pps-ea-ppsea-ssh",
//        "default",
//        new BasicSSHUserPrivateKey.DirectEntryPrivateKeySource("TODO_ADD_PRIVATE_KEY"),
//        "",
//        "pps-ea-ppsea-ssh")
//)


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

// "nexus-npm-repo-token" for npm builds
println("Creating \"nexus-npm-repo-token\" secret text..")
store.addCredentials(domain, new StringCredentialsImpl(
        CredentialsScope.GLOBAL,
        "nexus-npm-repo-token",
        "nexus-npm-repo-token",
        Secret.fromString(npmRepoToken))
)

println("Creating \"maven-deployment-token\" secret text..")
store.addCredentials(domain, new StringCredentialsImpl(
        CredentialsScope.GLOBAL,
        "maven-deployment-token",
        "maven-deployment-token",
        Secret.fromString(mavenDeploymentToken))
)