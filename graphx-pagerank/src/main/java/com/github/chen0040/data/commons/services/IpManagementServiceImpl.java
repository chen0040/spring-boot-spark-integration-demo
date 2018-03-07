package com.github.chen0040.data.commons.services;


import com.github.chen0040.lang.commons.utils.IpTools;


/**
 * Created by xschen on 23/12/2016.
 */
public class IpManagementServiceImpl implements IpManagementService {
   private String ipAddress;

   public IpManagementServiceImpl() {
      ipAddress = IpTools.getIpAddress();
   }

   @Override
   public void acquireIp() {
      ipAddress = IpTools.getIpAddress();
   }

   @Override
   public String getIpAddress() {
      return ipAddress;
   }
}
