variable "repository_name" {
  description = "Name of the MerchFlow ECR repository."
  type        = string
  default     = "merchflow-app-repo"
}

variable "image_tag_mutability" {
  description = "Whether image tags can be overwritten."
  type        = string
  default     = "MUTABLE"

  validation {
    condition     = contains(["MUTABLE", "IMMUTABLE"], var.image_tag_mutability)
    error_message = "image_tag_mutability must be MUTABLE or IMMUTABLE."
  }
}

variable "force_delete" {
  description = "Whether to allow deleting the repository when it contains images."
  type        = bool
  default     = true
}

variable "scan_on_push" {
  description = "Whether to scan images when they are pushed."
  type        = bool
  default     = true
}

variable "encryption_type" {
  description = "Encryption type for images stored in the repository."
  type        = string
  default     = "AES256"

  validation {
    condition     = contains(["AES256", "KMS"], var.encryption_type)
    error_message = "encryption_type must be AES256 or KMS."
  }
}

variable "repository_tags" {
  description = "Tags to apply to the MerchFlow ECR repository."
  type        = map(string)
  default = {
    Environment = "dev"
    ManagedBy   = "terraform"
  }
}
