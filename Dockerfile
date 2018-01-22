FROM registry.access.redhat.com/openshift3/jenkins-1-rhel7

# The default Jenkins login is "admin:password"
ENV JENKINS_PASSWORD password

# Setup environment variables
ENV OPENSHIFT_JENKINS_JVM_ARCH i386

# Configure Proxy for PluginManager
#COPY resources/jenkins/configuration/proxy.xml /var/lib/jenkins/proxy.xml
#COPY src/configure-proxy-for-pluginmanager.groovy /usr/share/jenkins/ref/init.groovy.d/configure-proxy-for-pluginmanager.groovy
#RUN curl --user 'admin:password' --data-urlencode "script=$(< /usr/share/jenkins/ref/init.groovy.d/configure-proxy-for-pluginmanager.groovy)" http://jenkins/scriptText

# Install plugins
#COPY resources/plugins.txt /usr/share/jenkins/plugins.txt
#RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt

# Configure "Globale Credentials"
#COPY src/configure-global-credentials.groovy /usr/share/jenkins/ref/init.groovy.d/configure-global-credentials.groovy

# Configure "Managed files"
#COPY src/configure-managed-files.groovy /usr/share/jenkins/ref/init.groovy.d/configure-managed-files.groovy

# Configure "In-process Script Approval"
#COPY resources/jenkins/configuration/scriptApproval.xml /var/lib/jenkins/scriptApproval.xml
#COPY src/configure-in-process-script-approval.groovy /usr/share/jenkins/ref/init.groovy.d/configure-in-process-script-approval.groovy

# Configure "Global Tool Configuration"
#COPY src/configure-global-tools.groovy /usr/share/jenkins/ref/init.groovy.d/configure-global-tools.groovy

# Configure Jenkins itself
#COPY src/configure-jenkins-itself.groovy /usr/share/jenkins/ref/init.groovy.d/configure-jenkins-itself.groovy

