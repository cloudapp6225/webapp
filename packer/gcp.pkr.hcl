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
  default = "./systemd/webapp.service"
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
  #  provisioner "file" {  # we pass .env file while creating instance form terraform
  #    source = "${var.source_env}"
  #    destination = "/tmp/.env"
  #  }
  #  provisioner "file" {
  #    source      = "${var.source_systemd}"
  #    destination = "/tmp/webapp.service"
  #  }
  provisioner "shell" {
    inline = [
      "sudo dnf upgrade -y",
      "sudo groupadd csye6225",
      "sudo useradd -s /sbin/nologin -g csye6225 -d /opt/csye6225 -m csye6225",
      "sudo mv /tmp/web-app-0.0.1-SNAPSHOT.jar /opt/csye6225/"
    ]
  }
  provisioner "shell" {
    script = "packer/gmi-script.sh"
  }
  provisioner "shell" {
    inline = [
      "sudo chown csye6225:csye6225 /opt/csye6225/web-app-0.0.1-SNAPSHOT.jar",
      "sudo chmod 750 /opt/csye6225/web-app-0.0.1-SNAPSHOT.jar"
    ]
  }
}