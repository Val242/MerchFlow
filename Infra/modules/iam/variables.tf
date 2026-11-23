variable "iam_user_name" {
  description = "Name of the IAM user."
  type        = string
  default     = "jenkins-user"
}

variable "iam_user_path" {
  description = "Path for the IAM user."
  type        = string
  default     = "/"
}

variable "iam_user_tags" {
  description = "Tags to apply to the IAM user."
  type        = map(string)
  default = {
    ManagedBy = "terraform"
  }
}

variable "iam_policy_name" {
  description = "Name of the inline policy attached to the IAM user."
  type        = string
  default     = "jenkins-permissions"
}