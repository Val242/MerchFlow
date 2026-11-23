resource "aws_iam_user" "jenkins-user" {
  name = var.iam_user_name
  path = var.iam_user_path

  tags = var.iam_user_tags
}

data "aws_iam_policy_document" "jenkins_permissions" {
  statement {
    effect = "Allow"
    actions = [
      "s3:ListAllMyBuckets",
      "ec2:Describe*",
      "ecr:*",
      "ecs:*"
    ]
    resources = ["*"]
  }
}

resource "aws_iam_user_policy" "jenkins_permissions" {
  name   = var.iam_policy_name
  user   = aws_iam_user.jenkins-user.name
  policy = data.aws_iam_policy_document.jenkins_permissions.json
}

resource "aws_iam_access_key" "jenkins_user_key" {
  user = aws_iam_user.jenkins-user.name
}