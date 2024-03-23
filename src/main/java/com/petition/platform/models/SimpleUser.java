package com.petition.platform.models;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.UUID;

@Entity
public class SimpleUser extends AbstractUser{

}
