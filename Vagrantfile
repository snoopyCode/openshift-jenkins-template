$script = <<SCRIPT

# Install Docker and docker-compose
sudo apt-get update
sudo apt-get install curl
sudo curl https://get.docker.com | bash
sudo docker version
sudo curl -L https://github.com/docker/compose/releases/download/1.18.0-rc1/docker-compose-Linux-x86_64 -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
sudo docker-compose -v

# Get the latest OpenShift Jenkins-Persistent Image
sudo docker pull registry.access.redhat.com/openshift3/jenkins-1-rhel7

# Start the Jenkins Container
cd /vagrant/
sudo docker-compose up -d
SCRIPT

Vagrant.configure("2") do |config|
    config.vm.box = "ubuntu/trusty64"
    config.vm.host_name = "jenkins"
    config.vm.provider :virtualbox do |vb|
        vb.customize ["modifyvm", :id, "--cpus", 4, "--memory", 4096]
    end
    config.vm.network :forwarded_port, guest: 8080, host: 8080
    config.vm.network :forwarded_port, guest: 50000, host: 50000
    config.vm.provision "shell", inline: $script
end
