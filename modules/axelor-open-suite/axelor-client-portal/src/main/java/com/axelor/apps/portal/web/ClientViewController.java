/*
 * Axelor Business Solutions
 *
 * Copyright (C) 2020 Axelor (<http://axelor.com>).
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.axelor.apps.portal.web;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.helpdesk.db.Ticket;
import com.axelor.apps.portal.service.ClientViewService;
import com.axelor.apps.project.db.Project;
import com.axelor.apps.sale.db.SaleOrder;
import com.axelor.apps.sale.db.repo.SaleOrderRepository;
import com.axelor.apps.stock.db.StockMove;
import com.axelor.apps.stock.db.repo.StockMoveRepository;
import com.axelor.auth.db.User;
import com.axelor.exception.service.TraceBackService;
import com.axelor.i18n.I18n;
import com.axelor.inject.Beans;
import com.axelor.meta.schema.actions.ActionView;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.team.db.TeamTask;
import java.util.Map;

public class ClientViewController {

  public void completeClientViewIndicators(ActionRequest request, ActionResponse response) {
    try {
      Map<String, Object> map;
      map = Beans.get(ClientViewService.class).updateClientViewIndicators();

      response.setValues(map);
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  /* SALEORDER OnCLick */
  public void showClientMyOrdersInProgress(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getOrdersInProgressOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("Orders in progress"))
              .model(SaleOrder.class.getName())
              .add("grid", "sale-order-grid-client")
              .add("form", "sale-order-form-client")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  public void showClientMyQuotation(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getQuotationsOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("My quotations"))
              .model(SaleOrder.class.getName())
              .add("grid", "sale-order-grid")
              .add("form", "sale-order-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  public void showClientMyLastOrder(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getLastOrderOfUser(clientUser);
      SaleOrder saleOrder = Beans.get(SaleOrderRepository.class).all().filter(domain).fetchOne();
      if (saleOrder != null) {
        response.setView(
            ActionView.define(I18n.get("Last order"))
                .model(SaleOrder.class.getName())
                .add("form", "sale-order-form-client")
                .context("_showRecord", saleOrder.getId())
                .map());
      }
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  /* PROJECT OnClick */
  public void showClientMyTotalProjects(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getTotalProjectsOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("Total projects"))
              .model(Project.class.getName())
              .add("grid", "project-grid")
              .add("form", "project-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  public void showClientMyNewTasks(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getNewTasksOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("New tasks"))
              .model(TeamTask.class.getName())
              .add("grid", "team-task-grid")
              .add("form", "team-task-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  public void showClientMyTasksInProgress(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getTasksInProgressOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("Tasks in progress"))
              .model(TeamTask.class.getName())
              .add("grid", "team-task-grid")
              .add("form", "team-task-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  public void showClientMyTasksDue(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getTasksDueOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("Tasks due"))
              .model(TeamTask.class.getName())
              .add("grid", "team-task-grid")
              .add("form", "team-task-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  /* STOCKMOVE OnClick */
  public void showClientMyLastDelivery(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getLastDeliveryOfUser(clientUser);
      StockMove stockMove = Beans.get(StockMoveRepository.class).all().filter(domain).fetchOne();
      if (stockMove != null) {
        response.setView(
            ActionView.define(I18n.get("Last delivery"))
                .model(StockMove.class.getName())
                .add("form", "stock-move-form")
                .context("_showRecord", stockMove.getId())
                .domain(domain)
                .map());
      }
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  public void showClientMyNextDelivery(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getNextDeliveryOfUser(clientUser);
      StockMove stockMove = Beans.get(StockMoveRepository.class).all().filter(domain).fetchOne();
      if (stockMove != null) {
        response.setView(
            ActionView.define(I18n.get("Next delivery"))
                .model(StockMove.class.getName())
                .add("form", "stock-move-form")
                .context("_showRecord", stockMove.getId())
                .domain(domain)
                .map());
      }
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  public void showClientMyPlannedDeliveries(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getPlannedDeliveriesOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("Planned deliveries"))
              .model(StockMove.class.getName())
              .add("grid", "stock-move-grid")
              .add("form", "stock-move-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  public void showClientReversions(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getReversionsOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("My reversions"))
              .model(StockMove.class.getName())
              .add("grid", "stock-move-grid")
              .add("form", "stock-move-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  /* INVOICE OnClick */
  public void showClientMyOverdueInvoices(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getOverdueInvoicesOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("Overdue invoices"))
              .model(Invoice.class.getName())
              .add("grid", "invoice-grid")
              .add("form", "invoice-client-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  public void showClientMyAwaitingInvoices(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getAwaitingInvoicesOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("Awaiting invoices"))
              .model(Invoice.class.getName())
              .add("grid", "invoice-grid")
              .add("form", "invoice-client-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  public void showClientMyTotalRemaining(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getTotalRemainingOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("Total remaining"))
              .model(Invoice.class.getName())
              .add("grid", "invoice-grid")
              .add("form", "invoice-client-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  public void showClientMyRefund(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getRefundOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("My refund"))
              .model(Invoice.class.getName())
              .add("grid", "invoice-refund-grid")
              .add("form", "invoice-client-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  /* TICKETS OnClick */
  public void showClientMyCustomerTickets(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getTicketsOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("Customer tickets"))
              .model(Ticket.class.getName())
              .add("grid", "ticket-grid")
              .add("form", "ticket-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  public void showClientMyCompanyTickets(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getCompanyTicketsOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("Company tickets"))
              .model(Ticket.class.getName())
              .add("grid", "ticket-grid")
              .add("form", "ticket-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  public void showClientMyResolvedTickets(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getResolvedTicketsOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("Resolved tickets"))
              .model(Ticket.class.getName())
              .add("grid", "ticket-grid")
              .add("form", "ticket-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }

  public void showClientMyLateTickets(ActionRequest request, ActionResponse response) {
    try {
      ClientViewService clientViewService = Beans.get(ClientViewService.class);
      User clientUser = clientViewService.getClientUser();
      String domain = clientViewService.getLateTicketsOfUser(clientUser);
      response.setView(
          ActionView.define(I18n.get("Late tickets"))
              .model(Ticket.class.getName())
              .add("grid", "ticket-grid")
              .add("form", "ticket-form")
              .domain(domain)
              .map());
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }
}
