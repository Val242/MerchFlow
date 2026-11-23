output "iam_user_name" {
  description = "Name of the IAM user."
  value       = module.iam.iam_user_name
}

output "iam_user_arn" {
  description = "ARN of the IAM user."
  value       = module.iam.iam_user_arn
}

output "iam_user_unique_id" {
  description = "Unique ID of the IAM user."
  value       = module.iam.iam_user_unique_id
}

output "iam_policy_name" {
  description = "Name of the inline policy attached to the IAM user."
  value       = module.iam.iam_policy_name
}

output "access_key_id" {
  description = "Access key ID for the IAM user."
  value       = module.iam.access_key_id
}

output "secret_access_key" {
  description = "Secret access key for the IAM user."
  value       = module.iam.secret_access_key
  sensitive   = true
}