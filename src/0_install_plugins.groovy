import jenkins.model.*
import java.util.concurrent.Future
import java.util.concurrent.LinkedBlockingQueue
import hudson.model.UpdateCenter
import jenkins.RestartRequiredException


println("Setting up proxy..")
def instance = Jenkins.getInstance()
instance.proxy = new hudson.ProxyConfiguration(
        "proxy.muc",
        8080,
        "${PROXY_USER_FROM_SECRET}",
        "${PROXY_PASS_FROM_SECRET}",
        "*.muc\n*.bmwgroup.net\nlocalhost\n127.0.0.1"
)
instance.save()
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


println("Installing plugins..")
def updateCenter = instance.updateCenter
updateCenter.updateAllSites()
def queue = new LinkedBlockingQueue<Future<UpdateCenter>>()
queue.add(updateCenter.getPlugin("config-file-provider").deploy())
queue.add(updateCenter.getPlugin("maven-plugin").deploy())
queue.add(updateCenter.getPlugin("nodejs").deploy())
queue.add(updateCenter.getPlugin("docker-plugin").deploy())
queue.add(updateCenter.getPlugin("kubernetes").deploy())
queue.add(updateCenter.getPlugin("openshift-sync").deploy())
queue.add(updateCenter.getPlugin("openshift-login").deploy())
queue.add(updateCenter.getPlugin("openshift-pipeline").deploy())
queue.add(updateCenter.getPlugin("pipeline-npm").deploy())
queue.collect { it.get() }
if (updateCenter.isRestartRequiredForCompletion()) {
    println("Restart required")
    Jenkins.instance.restart()
    throw new RestartRequiredException(null)
} else {
    println("Installing plugins done")
}
