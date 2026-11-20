variable "my_ip_cidr" {
  description = "Administrator public IP in CIDR notation, restricted to a single /32 address."
  type        = string

  validation {
    condition     = can(cidrnetmask(var.my_ip_cidr)) && endswith(var.my_ip_cidr, "/32")
    error_message = "my_ip_cidr must be a valid IPv4 CIDR containing one address, such as 203.0.113.10/32."
  }
}