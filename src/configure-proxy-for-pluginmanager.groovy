import jenkins.model.*

def instance = Jenkins.getInstance()
instance.proxy = new hudson.ProxyConfiguration(
        "proxy.muc",
        8080,
        "TODO_ADD_PROXY_USER", //TODO
        "TODO_ADD_PROXY_PASS", //TODO
        "*.muc\n*.bmwgroup.net\nlocalhost\n127.0.0.1"
)
instance.save()