# Source: https://github.com/openshift/jenkins/blob/master/2/Dockerfile.rhel7
FROM registry.access.redhat.com/openshift3/jenkins-2-rhel7

# Setup environment variables
ENV OPENSHIFT_JENKINS_JVM_ARCH i386

RUN mkdir /var/lib/jenkins/init.groovy.d

# Configure Proxy for PluginManager
COPY src/configure-proxy-for-pluginmanager.groovy /var/lib/jenkins/groovy.d/configure-proxy-for-pluginmanager.groovy

# Install plugins
COPY plugins.txt /opt/openshift/configuration/plugins.txt
RUN /usr/local/bin/install-plugins.sh /opt/openshift/configuration/plugins.txt

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

