packer {
  required_plugins {
    googlecompute = {
      version = ">= 1.0.0"
      source  = "github.com/hashicorp/googlecompute"
    }
  }
}

variable "project_id" {
  type = string
  default = "dev-packer"
}
variable "zone" {
  type = string
  default = "us-east1-b"
}

source "googlecompute" "centOS-image" {
  project_id              = var.project_id
  zone                    = var.zone
  source_image_family     = "centos-stream-8"
  image_name              = "packer-centos8-${formatdate("YYYY-MM-DD-hh-mm-ss", timestamp())}"
  ssh_username            = "packer"
  tags                    = ["packer-image-centos8"]
  network                 = "default"
  image_guest_os_features = ["PRIVATE"]
}

build {
  sources = ["sources.googlecompute.centOS-image"]
  provisioner "shell" {
    script = "packer/gmi-script.sh"
  }
}

