// Delete init marker file
println("Deleting init marker file \"/var/lib/jenkins/configured\"..")
if(new File("/var/lib/jenkins/configured").delete()) {
	println("Init marker file deleted")
} else {
	println("Failed to delete init marker file!")
}
