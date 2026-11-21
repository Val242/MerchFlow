#!/bin/bash
set -e

echo "Updating system packages..."
sudo apt-get update -y

echo "Installing fontconfig and OpenJDK 17..."
sudo apt-get install -y fontconfig openjdk-17-jre curl

echo "Verifying Java installation..."
java -version

echo "Adding Jenkins GPG key and repository..."
sudo mkdir -p /etc/apt/keyrings
sudo wget -O /etc/apt/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key

echo "deb [signed-by=/etc/apt/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/" | \
  sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null

echo "Updating package index and installing Jenkins..."
sudo apt-get update -y
sudo apt-get install -y jenkins

echo "Enabling and starting Jenkins service..."
sudo systemctl enable jenkins
sudo systemctl start jenkins

echo "----------------------------------------------------"
echo "Jenkins installation complete!"
echo "Service Status:"
sudo systemctl status jenkins --no-pager | head -n 5
echo "----------------------------------------------------"
echo "Initial Admin Password:"
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
echo "----------------------------------------------------"
