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

// "http-proxy" for npm builds
println("Creating \"http-proxy\" secret text..")
store.addCredentials(domain, new StringCredentialsImpl(
        CredentialsScope.GLOBAL,
        "http-proxy",
        "http-proxy",
        Secret.fromString("http://TODO_USER:TODO_PASS@proxy.muc:8080")) //TODO hide this in a secret
)

// "https-proxy" for npm builds
println("Creating \"https-proxy\" secret text..")
store.addCredentials(domain, new StringCredentialsImpl(
        CredentialsScope.GLOBAL,
        "https-proxy",
        "https-proxy",
        Secret.fromString("http://TODO_USER:TODO_PASS@proxy.muc:8080")) ///TODO hide this in a secret
)

// "nexus-npm-repo-token" for npm builds
println("Creating \"nexus-npm-repo-token\" secret text..")
store.addCredentials(domain, new StringCredentialsImpl(
        CredentialsScope.GLOBAL,
        "nexus-npm-repo-token",
        "nexus-npm-repo-token",
        Secret.fromString("TODO_TOKEN")) //TODO hide this in a secret
)