import jenkins.model.Jenkins

instance = Jenkins.getInstance()

// Set quiet period to 270 sec
println("Setting quiet period..")
instance.setQuietPeriod(270)