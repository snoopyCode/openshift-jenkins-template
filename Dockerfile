# Source: https://github.com/openshift/jenkins/blob/master/2/Dockerfile.rhel7
FROM registry.access.redhat.com/openshift3/jenkins-2-rhel7

MAINTAINER Chris Troeger <Christopher.Troeger@iteratec.de>

# Copy config file templates
RUN mkdir /opt/openshift/configuration/configFileTemplates
COPY configuration/configFileTemplates/* /opt/openshift/configuration/configFileTemplates/

# Copy post-init scripts
RUN mkdir /opt/openshift/configuration/init.groovy.d
COPY configuration/init.groovy.d/* /opt/openshift/configuration/init.groovy.d/

# FIXME try to create something directly in /var/lib/jenkins
COPY notes.txt /var/lib/jenkins/notes.txt

# Copy config file templates
#RUN mkdir /var/lib/jenkins/configFileTemplates
#COPY configuration/configFileTemplates/* /var/lib/jenkins/configFileTemplates/

# Copy post-init scripts
#RUN mkdir /var/lib/jenkins/init.groovy.d
#COPY configuration/init.groovy.d/* /var/lib/jenkins/init.groovy.d/

# Overwrite the parent entrypoint and start the source-to-image run (Source: https://github.com/openshift/jenkins/blob/master/2/contrib/s2i/run )
USER 1001
ENTRYPOINT ["/usr/bin/dumb-init", "--"]
CMD ["/usr/libexec/s2i/run"]