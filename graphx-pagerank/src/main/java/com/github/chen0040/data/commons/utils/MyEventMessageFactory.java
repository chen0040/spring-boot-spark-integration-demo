package com.github.chen0040.data.commons.utils;


import com.github.chen0040.data.commons.consts.SparkGraphMinerCommand;
import com.github.chen0040.data.commons.messages.SystemEventMessage;
import com.github.chen0040.data.commons.models.MyWorkerCompleted;
import com.github.chen0040.data.commons.models.MyWorkerProgress;


/**
 * Created by xschen on 19/1/2017.
 */
public class MyEventMessageFactory {


   public static SystemEventMessage createMessage4GraphMiningCompleted(MyWorkerCompleted workerCompleted) {
      int changedRowCount = workerCompleted.getChangedRows();
      return createMessage4GraphMiningCompleted(workerCompleted.getMessage(), changedRowCount);
   }


   public static SystemEventMessage createMessage4GraphMiningProgress(MyWorkerProgress workerProgress) {
      int percentage = workerProgress.getPercentage();
      int remainingTime = workerProgress.getRemainingTime();
      boolean valid = workerProgress.isSuccessful();
      String error = workerProgress.getError();
      int errorRow = workerProgress.getErrorRows();

      boolean done = percentage == 100;


      SystemEventMessage message = new SystemEventMessage();
      message.setUserId(-1);
      message.setAttribute("percentage", "" + percentage);
      message.setAttribute("done", "" + done);
      message.setAttribute("remainingTime", "" + remainingTime);
      message.setAttribute("error", error);
      message.setAttribute("errorRows", "" + errorRow);
      message.setName(SparkGraphMinerCommand.COMMAND_GRAPH_MINING + "-progress");
      message.setAttribute("msg", workerProgress.getMessage());

      message.setAttribute("success", "" + valid);
      return message;
   }


   public static SystemEventMessage createMessage4GraphMiningCompleted(String msg, long changedRowCount) {
      final String eventName = SparkGraphMinerCommand.COMMAND_GRAPH_MINING + "-completed";
      SystemEventMessage message = new SystemEventMessage();
      message.setUserId(-1);
      message.setName(eventName);
      message.setAttribute("changed", "" + changedRowCount);
      message.setAttribute("success", "true");
      message.setAttribute("msg", msg);
      return message;
   }

   public static SystemEventMessage createMessage4GraphMiningEvent(String msg) {
      final String eventName = SparkGraphMinerCommand.COMMAND_GRAPH_MINING + "-event";
      SystemEventMessage message = new SystemEventMessage();
      message.setUserId(-1);
      message.setName(eventName);
      message.setAttribute("changed", "*");
      message.setAttribute("success", "true");
      message.setAttribute("msg", msg);
      return message;
   }
}
