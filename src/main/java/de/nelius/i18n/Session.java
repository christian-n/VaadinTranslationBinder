package de.nelius.i18n;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;


public class Session
{

   private String id;
   private VaadinRequest request;
   private Map<String, Object> attributes = new HashMap<>();

   private Session(String id)
   {
      this.id = id;
   }

   private Session(String id, VaadinRequest request)
   {
      this.id = id;
      this.request = request;
   }

   public String getId()
   {
      return id;
   }

   public VaadinRequest getRequest()
   {
      return request;
   }

   public static Session of(String id)
   {
      return new Session(id);
   }

   public static Session of(String id, VaadinRequest request)
   {
      return new Session(id, request);
   }

   public Session with(String key, Object value)
   {
      attributes.put(key, value);
      return this;
   }

   public static Session get()
   {
      return new Session(VaadinSession.getCurrent().getSession().getId(), VaadinRequest.getCurrent());
   }

   public Object attribute(String key)
   {
      return attributes.get(key);
   }

   @Override
   public boolean equals(Object o)
   {
      if( this == o )
      {
         return true;
      }
      if( o == null || getClass() != o.getClass() )
      {
         return false;
      }

      Session session = (Session) o;

      return id != null ? id.equals(session.id) : session.id == null;
   }

   @Override
   public int hashCode()
   {
      return id != null ? id.hashCode() : 0;
   }

}
