data "aws_vpc" "default" {
  default = true
}

resource "aws_security_group" "jenkins_server" {
  name        = "merchflow-jenkins-server"
  description = "Security group for the MerchFlow Jenkins server."
  vpc_id      = data.aws_vpc.default.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "nexus_server" {
  name        = "merchflow-nexus-server"
  description = "Security group for the MerchFlow Nexus server."
  vpc_id      = data.aws_vpc.default.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "sonar_server" {
  name        = "merchflow-sonar-server"
  description = "Security group for the MerchFlow SonarQube server."
  vpc_id      = data.aws_vpc.default.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group_rule" "jenkins_ssh_from_my_ip" {
  type              = "ingress"
  security_group_id = aws_security_group.jenkins_server.id
  description       = "SSH access to Jenkins from the administrator IP."
  from_port         = 22
  to_port           = 22
  protocol          = "tcp"
  cidr_blocks       = [var.my_ip_cidr]
}

resource "aws_security_group_rule" "nexus_ssh_from_my_ip" {
  type              = "ingress"
  security_group_id = aws_security_group.nexus_server.id
  description       = "SSH access to Nexus from the administrator IP."
  from_port         = 22
  to_port           = 22
  protocol          = "tcp"
  cidr_blocks       = [var.my_ip_cidr]
}

resource "aws_security_group_rule" "sonar_ssh_from_my_ip" {
  type              = "ingress"
  security_group_id = aws_security_group.sonar_server.id
  description       = "SSH access to SonarQube from the administrator IP."
  from_port         = 22
  to_port           = 22
  protocol          = "tcp"
  cidr_blocks       = [var.my_ip_cidr]
}

resource "aws_security_group_rule" "jenkins_ssh_from_nexus" {
  type                     = "ingress"
  security_group_id        = aws_security_group.jenkins_server.id
  description              = "SSH access to Jenkins from the Nexus server."
  from_port                = 22
  to_port                  = 22
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.nexus_server.id
}

resource "aws_security_group_rule" "jenkins_ssh_from_sonar" {
  type                     = "ingress"
  security_group_id        = aws_security_group.jenkins_server.id
  description              = "SSH access to Jenkins from the SonarQube server."
  from_port                = 22
  to_port                  = 22
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.sonar_server.id
}

resource "aws_security_group_rule" "nexus_ssh_from_jenkins" {
  type                     = "ingress"
  security_group_id        = aws_security_group.nexus_server.id
  description              = "SSH access to Nexus from the Jenkins server."
  from_port                = 22
  to_port                  = 22
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.jenkins_server.id
}

resource "aws_security_group_rule" "nexus_ssh_from_sonar" {
  type                     = "ingress"
  security_group_id        = aws_security_group.nexus_server.id
  description              = "SSH access to Nexus from the SonarQube server."
  from_port                = 22
  to_port                  = 22
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.sonar_server.id
}

resource "aws_security_group_rule" "sonar_ssh_from_jenkins" {
  type                     = "ingress"
  security_group_id        = aws_security_group.sonar_server.id
  description              = "SSH access to SonarQube from the Jenkins server."
  from_port                = 22
  to_port                  = 22
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.jenkins_server.id
}

resource "aws_security_group_rule" "sonar_ssh_from_nexus" {
  type                     = "ingress"
  security_group_id        = aws_security_group.sonar_server.id
  description              = "SSH access to SonarQube from the Nexus server."
  from_port                = 22
  to_port                  = 22
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.nexus_server.id
}

resource "aws_security_group_rule" "jenkins_http_from_my_ip" {
  type              = "ingress"
  security_group_id = aws_security_group.jenkins_server.id
  description       = "HTTP access to Jenkins from the administrator IP."
  from_port         = 80
  to_port           = 80
  protocol          = "tcp"
  cidr_blocks       = [var.my_ip_cidr]
}

resource "aws_security_group_rule" "jenkins_8080_from_my_ip" {
  type              = "ingress"
  security_group_id = aws_security_group.jenkins_server.id
  description       = "Jenkins access on port 8080 from the administrator IP."
  from_port         = 8080
  to_port           = 8080
  protocol          = "tcp"
  cidr_blocks       = [var.my_ip_cidr]
}

resource "aws_security_group_rule" "jenkins_8080_from_nexus" {
  type                     = "ingress"
  security_group_id        = aws_security_group.jenkins_server.id
  description              = "Port 8080 access from the Nexus server."
  from_port                = 8080
  to_port                  = 8080
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.nexus_server.id
}

resource "aws_security_group_rule" "jenkins_8080_from_sonar" {
  type                     = "ingress"
  security_group_id        = aws_security_group.jenkins_server.id
  description              = "Port 8080 access from the SonarQube server."
  from_port                = 8080
  to_port                  = 8080
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.sonar_server.id
}

resource "aws_security_group_rule" "nexus_8081_from_jenkins" {
  type                     = "ingress"
  security_group_id        = aws_security_group.nexus_server.id
  description              = "Nexus access from the Jenkins server."
  from_port                = 8081
  to_port                  = 8081
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.jenkins_server.id
}

resource "aws_security_group_rule" "nexus_8081_from_my_ip" {
  type              = "ingress"
  security_group_id = aws_security_group.nexus_server.id
  description       = "Nexus access from the administrator IP."
  from_port         = 8081
  to_port           = 8081
  protocol          = "tcp"
  cidr_blocks       = [var.my_ip_cidr]
}

resource "aws_security_group_rule" "sonar_9000_from_jenkins" {
  type                     = "ingress"
  security_group_id        = aws_security_group.sonar_server.id
  description              = "SonarQube access from the Jenkins server."
  from_port                = 9000
  to_port                  = 9000
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.jenkins_server.id
}

resource "aws_security_group_rule" "sonar_9000_from_my_ip" {
  type              = "ingress"
  security_group_id = aws_security_group.sonar_server.id
  description       = "SonarQube access from the administrator IP."
  from_port         = 9000
  to_port           = 9000
  protocol          = "tcp"
  cidr_blocks       = [var.my_ip_cidr]
}