# Source: https://github.com/openshift/jenkins/blob/master/2/Dockerfile.rhel7
FROM registry.access.redhat.com/openshift3/jenkins-2-rhel7

MAINTAINER Chris Troeger (christopher.troeger@iteratec.de)

# Setup environment variables
ENV OPENSHIFT_JENKINS_JVM_ARCH i386
#ENV OPENSHIFT_ENABLE_OAUTH true
#ENV OPENSHIFT_ENABLE_REDIRECT_PROMPT true
#ENV KUBERNETES_MASTER https://kubernetes.default:443
#ENV KUBERNETES_TRUST_CERTIFICATES true

# Copy config file templates
COPY resources/* /usr/tmp/

# Install plugins
#COPY plugins/* /opt/openshift/plugins/
#RUN /usr/local/bin/install-plugins.sh < /usr/tmp/plugins.txt
#RUN mkdir /opt/openshift/plugins/
#RUN /usr/local/bin/install-plugins.sh maven-plugin:3.0
#nodejs:1.2.4 docker-plugin:1.0.4 kubernetes:1.1.3 openshift-sync:0.1.32 openshift-login:1.0.0 openshift-pipeline:1.0.52 config-file-provider:2.16.4 pipeline-npm:0.9.1


# Copy post-init scripts
RUN mkdir /var/lib/jenkins/init.groovy.d
COPY src/* /var/lib/jenkins/init.groovy.d/


