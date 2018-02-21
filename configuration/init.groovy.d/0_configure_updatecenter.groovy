import jenkins.model.*

// Set UpdateCenter proxy
def env = System.getenv()
def instance = Jenkins.getInstance()
if (env["USE_PROXY"] != null && env["USE_PROXY"] == "true") {
    String proxyHost = env["PROXY_HOST_FROM_SECRET"]
    String proxyPort = env["PROXY_PORT_FROM_SECRET"]
    String proxyUser = env["PROXY_USER_FROM_SECRET"]
    String proxyPass = env["PROXY_PASS_FROM_SECRET"]
    println("Setting up UpdateCenter proxy..")
    instance.proxy = new hudson.ProxyConfiguration(
            proxyHost,
            proxyPort.toInteger(),
            proxyUser,
            proxyPass,
            "*.muc\n*.bmwgroup.net\nlocalhost\n127.0.0.1"
    )
    instance.save()
}

// Refresh UpdateCenter
println("Refreshing UpdateCenter..")
instance.pluginManager.doCheckUpdatesServer()
instance.updateCenter.updateAllSites()
