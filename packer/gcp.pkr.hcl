packer {
  required_plugins {
    googlecompute = {
      version = ">= 1.0.0"
      source  = "github.com/hashicorp/googlecompute"
    }
  }
}
variable "project_id" {
  type    = string
  default = ""
}
variable "zone" {
  type    = string
  default = "us-east1-b"
}
variable "source_jar" {
  type    = string
  default = ""
}
variable "image_family" {
  type    = string
  default = "centos-stream-8"
}
variable "source_systemd" {
  type    = string
  default = ""
}
variable "source_env" {
  type    = string
  default = ""
}
source "googlecompute" "centOS-image" {
  project_id              = var.project_id
  zone                    = var.zone
  source_image_family     = var.image_family
  image_name              = "packer-centos8-${formatdate("YYYY-MM-DD-hh-mm-ss", timestamp())}"
  ssh_username            = "packer"
  tags                    = ["packer-image-centos8"]
  network                 = "default"
  image_guest_os_features = ["PRIVATE"]
}

build {
  sources = ["sources.googlecompute.centOS-image"]
  provisioner "file" {
    source      = "${var.source_jar}"
    destination = "/tmp/web-app-0.0.1-SNAPSHOT.jar"
  }
  #  provisioner "file" { # we pass .env file from github actions
  #    source      = "${var.source_env}"
  #    destination = "/tmp/.env"
  #  }
  provisioner "file" {
    source      = "${var.source_systemd}"
    destination = "/tmp/webapp.service"
  }
  provisioner "shell" {
    script = "packer/gmi-script.sh"
  }
}