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

def qqUserName = env["QQ_USER_NAME"]?.trim()
def qqUserPassword = env["QQ_USER_PASSWORD"]?.trim()

// Set proxy
if (env["USE_PROXY"] != null && env["USE_PROXY"] == "true") {
    String proxyHost = env["PROXY_HOST_FROM_SECRET"]
    String proxyPort = env["PROXY_PORT_FROM_SECRET"]
    String proxyUser = env["PROXY_USER_FROM_SECRET"]
    String proxyPass = env["PROXY_PASS_FROM_SECRET"]
    // "http-proxy" for npm builds
    println("Creating \"http-proxy\" secret text..")
    store.addCredentials(domain, new StringCredentialsImpl(
            CredentialsScope.GLOBAL,
            "http-proxy",
            "http-proxy",
            Secret.fromString("http://" + proxyUser + ":" + proxyPass + "@"+ proxyHost + ":" + proxyPort))
    )

    // "https-proxy" for npm builds
    println("Creating \"https-proxy\" secret text..")
    store.addCredentials(domain, new StringCredentialsImpl(
            CredentialsScope.GLOBAL,
            "https-proxy",
            "https-proxy",
            Secret.fromString("http://" + proxyUser + ":" + proxyPass + "@"+ proxyHost + ":" + proxyPort))
    )
}

// Used in 5_configure-jenkins-itself.groovy
println("Creating QQ User credentials...")

if (!qqUserName || !qqUserPassword) {
    println("Missing QQ User credentials. Skipping...")
} else {
    store.addCredentials(domain, new UsernamePasswordCredentialsImpl(
            CredentialsScope.GLOBAL,
            "qq-user",
            "User that is allowed to interact with the Build REST API of the Bitbucket Server Repositories and the JIRA REST API",
            qqUserName,
            qqUserPassword
    ))
}