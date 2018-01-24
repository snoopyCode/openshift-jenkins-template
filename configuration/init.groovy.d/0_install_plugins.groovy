import jenkins.model.*
import java.util.concurrent.Future
import java.util.concurrent.LinkedBlockingQueue
import hudson.model.UpdateCenter
import jenkins.RestartRequiredException


// Set proxy
def env = System.getenv()
def instance = Jenkins.getInstance()
if (env["USE_PROXY"] != null && env["USE_PROXY"] == "true") {
    String proxyUserBase64 = env["PROXY_USER_FROM_SECRET"]
    String proxyPassBase64 = env["PROXY_PASS_FROM_SECRET"]
    String proxyUser = new String(proxyUserBase64.decodeBase64())
    String proxyPass = new String(proxyPassBase64.decodeBase64())
    println("Setting up proxy..")
    instance.proxy = new hudson.ProxyConfiguration(
            "proxy.muc",
            8080,
            proxyUser,
            proxyPass,
            "*.muc\n*.bmwgroup.net\nlocalhost\n127.0.0.1"
    )
    instance.save()
}

// Install plugins
println("Installing plugins..")
def updateCenter = instance.updateCenter
updateCenter.updateAllSites()
def queue = new LinkedBlockingQueue<Future<UpdateCenter>>()
//FIXME use specific plugin versions
//maven-plugin:3.0
//nodejs:1.2.4
//docker-plugin:1.0.4
//kubernetes:1.1.3
//openshift-sync:0.1.32
//openshift-login:1.0.0
//openshift-pipeline:1.0.52
//config-file-provider:2.16.4
//pipeline-npm:0.9.1
queue.add(updateCenter.getPlugin("config-file-provider").deploy())
queue.add(updateCenter.getPlugin("maven-plugin").deploy())
queue.add(updateCenter.getPlugin("nodejs").deploy())
queue.add(updateCenter.getPlugin("docker-plugin").deploy())
queue.add(updateCenter.getPlugin("kubernetes").deploy())
queue.add(updateCenter.getPlugin("openshift-sync").deploy())
queue.add(updateCenter.getPlugin("openshift-login").deploy())
queue.add(updateCenter.getPlugin("openshift-pipeline").deploy())
queue.add(updateCenter.getPlugin("openshift-client").deploy())
queue.add(updateCenter.getPlugin("pipeline-npm").deploy())
queue.collect { it.get() }
if (updateCenter.isRestartRequiredForCompletion()) {
    println("Restart required")
    Jenkins.instance.restart()
    throw new RestartRequiredException(null)
} else {
    println("Installing plugins done")
}
