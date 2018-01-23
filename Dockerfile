# Source: https://github.com/openshift/jenkins/blob/master/2/Dockerfile.rhel7
FROM registry.access.redhat.com/openshift3/jenkins-2-rhel7

MAINTAINER Chris Troeger (christopher.troeger@iteratec.de)

# Setup environment variables
ENV OPENSHIFT_JENKINS_JVM_ARCH i386
ENV OPENSHIFT_ENABLE_OAUTH true
ENV OPENSHIFT_ENABLE_REDIRECT_PROMPT true
ENV KUBERNETES_MASTER https://kubernetes.default:443
ENV KUBERNETES_TRUST_CERTIFICATES true

# Copy config file templates
COPY resources/* /usr/tmp/

# Copy post-init scripts
RUN mkdir /var/lib/jenkins/init.groovy.d
COPY src/* /var/lib/jenkins/init.groovy.d/


