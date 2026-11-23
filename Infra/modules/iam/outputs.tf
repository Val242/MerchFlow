output "iam_user_name" {
  description = "Name of the IAM user."
  value       = aws_iam_user.jenkins-user.name
}

output "iam_user_arn" {
  description = "ARN of the IAM user."
  value       = aws_iam_user.jenkins-user.arn
}

output "iam_user_unique_id" {
  description = "Unique ID of the IAM user."
  value       = aws_iam_user.jenkins-user.unique_id
}

output "iam_policy_name" {
  description = "Name of the inline policy attached to the IAM user."
  value       = aws_iam_user_policy.jenkins_permissions.name
}

output "access_key_id" {
  description = "Access key ID for the IAM user."
  value       = aws_iam_access_key.jenkins_user_key.id
}

output "secret_access_key" {
  description = "Secret access key for the IAM user."
  value       = aws_iam_access_key.jenkins_user_key.secret
  sensitive   = true
}