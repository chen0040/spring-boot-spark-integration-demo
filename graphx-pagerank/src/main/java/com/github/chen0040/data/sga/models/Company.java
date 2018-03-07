package com.github.chen0040.data.sga.models;


import com.github.chen0040.data.commons.models.CompanyContract;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 30/9/2016.
 * Company entity that represent company which advertise jobs
 */
@Entity
@Getter
@Setter
@Table(name = "companies")
public class Company extends BasicEntity implements Serializable, CompanyContract {

   private static final long serialVersionUID = -6184795277889462695L;

   private String name;


   private String description;

   private String link;

   private String address;

   private String email;

   @OneToMany(mappedBy="company", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
   private List<MyJob> jobs = new ArrayList<>();

}
