package buffer5_0;

import java.util.*;
import java.util.Map.Entry;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

class Event {
    String eventName;
    LocalTime startTime;
    LocalTime endTime;

    public Event(String eventName, LocalTime startTime, LocalTime endTime) {
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

public class Calendar_club {
	  Map<LocalDate, List<Event>> events = new HashMap<>(); // Initialize here
	    Queue<Event> todoList  = new LinkedList<>();
	    PriorityQueue<Notification> queue=new PriorityQueue<>(Comparator.comparingLong(Notification::getPriority));
	   // Notifications notifications = new Notifications();
	      
  Scanner sc = new Scanner(System.in);

  public Calendar_club() {
      events = new HashMap<>();
      todoList = new LinkedList<>();
      queue=new PriorityQueue<>(Comparator.comparingLong(Notification::getPriority));
      
  }

  public void addEvent(LocalDate date, Event event) {
	    if (!isEventOverlapWithWarning(date, event)) {
	        if (!events.containsKey(date)) {
	            events.put(date, new ArrayList<>());
	        }
	        events.get(date).add(event);
	       addNotification(event, date); 
	    }
	}


  private boolean isEventOverlapWithWarning(LocalDate date, Event newEvent) {
      if (events.containsKey(date)) {
          for (Event existingEvent : events.get(date)) {
              if (existingEvent.startTime.equals(newEvent.startTime)) {
                  System.out.println("Warning: Event '" + existingEvent.eventName + "' overlaps with the event you are trying to add on " + date + " at " + newEvent.startTime);
                  Scanner scanner = new Scanner(System.in);
                  System.out.print("Do you still want to continue with your event? (yes/no): ");
                  String choice = scanner.nextLine().trim().toLowerCase();
                  if (!choice.equals("yes")) {
                      System.out.println("Returning to home page...");
                      return true; 
                  }
              }
          }
      }
     
      return false; 
  }


  public void displayCalendar() {
      System.out.println("----- Calendar with Events -----");
      for (Entry<LocalDate, List<Event>> entry : events.entrySet()) {
          System.out.println(entry.getKey() + ":");
          List<Event> eventList = entry.getValue();
          int index = 1;
          for (Event event : eventList) {
              System.out.printf("\tEvent Number: %d\n", index);
              System.out.println("\tEvent: " + event.eventName);
              System.out.println("\tStart Time: " + event.startTime);
              System.out.println("\tEnd Time: " + event.endTime);
              System.out.println("=================================");
              
              
              index++;
          }
          System.out.println();
      }
  }

  public void registerForEvent() {
	 
	    System.out.println("Enter the date of the event you want to register for (e.g., 2024-02-21): ");
	    String dateStr = sc.next();
	    LocalDate date = LocalDate.parse(dateStr);
	    if (events.containsKey(date)) {
	        List<Event> eventList = events.get(date);
	        System.out.println("Enter the event number for which you want to register: ");
	        int eventNumber = sc.nextInt();
	        if (eventNumber >= 1 && eventNumber <= eventList.size()) {
	            Event selectedEvent = eventList.get(eventNumber - 1);
	            todoList.add(selectedEvent);
	            System.out.println("You have successfully registered for this event: " + selectedEvent.eventName);
	            System.out.println("Have fun at the event!!");
	        } else {
	            System.out.println("Invalid event number. Please try again.");
	        }
	    } else {
	        System.out.println("No events found on this date. Please try again.");
	    }
	}

  public void displayToDoList() {
	    System.out.println("----- To-Do List -----");

	    if (todoList!=null) {
	        for (Event event : todoList) {
	            System.out.println("Event Name: " + event.eventName);
	            System.out.println("Start Time: " + event.startTime);
	            System.out.println();
	        }
	    } else {
	        System.out.println("Your to-do list is currently empty.");
	    }
	    System.out.println("------------------------");
	}

  public void addNewEvent()
  {
  	addEvent(LocalDate.parse("2024-05-21"), new Event("Meeting", LocalTime.parse("09:00"), LocalTime.parse("10:00")));
      addEvent(LocalDate.parse("2024-05-21"), new Event("Lunch", LocalTime.parse("12:00"), LocalTime.parse("13:00")));
      addEvent(LocalDate.parse("2024-05-22"), new Event("Workshop", LocalTime.parse("14:00"), LocalTime.parse("16:00")));
  	System.out.println("Enter Event Name: ");
      String eventName = sc.nextLine();
      
      System.out.println("Enter Event Date (YYYY-MM-DD): ");
      String eventDateStr = sc.next();
      
      LocalDate eventDate = LocalDate.parse(eventDateStr);
      System.out.println("Enter Event Start Time (HH:MM): ");
      String startTimeStr = sc.next();
      
      LocalTime startTime = LocalTime.parse(startTimeStr);
      System.out.println("Enter Event End Time (HH:MM): ");
      String endTimeStr = sc.next();
      
      LocalTime endTime = LocalTime.parse(endTimeStr);
      addEvent(eventDate, new Event(eventName, startTime, endTime));
      

  }
  

  public  void addNotification(Event event, LocalDate date) {
      queue.offer(new Notification(event, date));
  }
  public void Notifications() {
      queue = new PriorityQueue<>(Comparator.comparingLong(Notification::getPriority));
  }

  
  public void displayNotifications() {
      System.out.println("----- Notifications -----");
      if (queue.isEmpty()) {
          System.out.println("No notifications.");
          return;
      }
      while (!queue.isEmpty()) {
          Notification notification = queue.poll();
          Event event = notification.event;
          LocalDate date = notification.date;
          System.out.println("Event: " + event.eventName);
          System.out.println("Date: " + date);
          System.out.println("Days until event: " + notification.getPriority());
          System.out.println();
      }
  }
 
  public void main(Calendar_club calendar) {
	 
     
	 calendar.displayCalendar();
	 calendar.registerForEvent();
	 calendar.displayToDoList();
    
  }
    
}
class Notification {
    Event event;
    LocalDate date;

    public Notification(Event event, LocalDate date) {
        this.event = event;
        this.date = date;
    }

    public long getPriority() {
        return ChronoUnit.DAYS.between(LocalDate.now(), date);
    }
}


