import jenkins.model.*

// Set UpdateCenter proxy
def env = System.getenv()
def instance = Jenkins.getInstance()
if (env["USE_PROXY"] != null && env["USE_PROXY"] == "true") {
    String proxyUserBase64 = env["PROXY_USER_FROM_SECRET"]
    String proxyPassBase64 = env["PROXY_PASS_FROM_SECRET"]
    String proxyUser = new String(proxyUserBase64.decodeBase64())
    String proxyPass = new String(proxyPassBase64.decodeBase64())
    println("Setting up UpdateCenter proxy..")
    instance.proxy = new hudson.ProxyConfiguration(
            "proxy.muc",
            8080,
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
