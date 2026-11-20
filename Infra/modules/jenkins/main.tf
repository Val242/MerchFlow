resource "aws_instance" "jenkins-server" {
  ami                    = var.ami_id
  instance_type          = var.instance_type
  key_name               = aws_key_pair.jenkins-key-pair.key_name
  vpc_security_group_ids = [var.jenkins_server_security_group_id]

  root_block_device {
    volume_size = 20
    volume_type = "gp3"
  }
  tags = {
    Name = var.instance_name
  }
}

resource "aws_ec2_instance_state" "jenkins-server-state" {
  instance_id = aws_instance.jenkins-server.id
  state       = "running"
}

resource "aws_key_pair" "jenkins-key-pair" {
  key_name   = var.key_name
  public_key = file("${path.module}/${var.app_public_key_path}")

  tags = {
    Name        = var.key_name
    Environment = var.environment
  }
}