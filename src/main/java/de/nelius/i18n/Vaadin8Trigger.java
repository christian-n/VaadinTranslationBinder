package de.nelius.i18n;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.server.*;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.spring.server.SpringVaadinServletService;
import com.vaadin.ui.UI;
import org.springframework.stereotype.Component;

@Component("vaadinServlet")
public class Vaadin8Trigger extends SpringVaadinServlet implements SessionInitListener, SessionDestroyListener {

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        event.getSession().addRequestHandler(new RequestHandler() {
            @Override
            public boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) throws IOException {
                return false;
            }
        });
        UiBinder.trigger(Session.of(event.getSession().getSession().getId()));
    }

    public class VaadinServiceDelegate extends SpringVaadinServletService {

        public VaadinServiceDelegate(VaadinServlet servlet, DeploymentConfiguration deploymentConfiguration, String serviceUrl) throws ServiceException {
            super(servlet, deploymentConfiguration, serviceUrl);
        }

        @Override
        public VaadinSession findVaadinSession(VaadinRequest request) throws ServiceException, SessionExpiredException {
            VaadinSession session = super.findVaadinSession(request);
            UiBinder.trigger(Session.of(session.getSession().getId()));
            return session;
        }
    }

    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        UiBinder.destroy(Session.of(event.getSession().getSession().getId()));
    }

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionDestroyListener(this);
        getService().addSessionInitListener(this);
    }

//    @Override
//    protected VaadinServletService createServletService(DeploymentConfiguration deploymentConfiguration) throws ServiceException {
//        VaadinServiceDelegate service = new VaadinServiceDelegate(
//                this, deploymentConfiguration, getServiceUrlPath());
//        service.init();
//        return service;
//    }

//    @Override
//    public void sessionInit(SessionInitEvent event) throws ServiceException {
//        UiBinder.trigger(Session.of(event.getSession().getSession().getId()));
//    }

}
