package com.poseidon.controllers.view;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poseidon.domain.Trade;
import com.poseidon.services.TradeService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/trade")
public class TradeController {

    private static final Logger LOGGER = LogManager
            .getLogger("TradeController");

    @Autowired
    private TradeService tradeService;

    /**
     * Get HTML page used to display all trades list.
     *
     * @param model
     * @return /trade/list.html page
     */
    @ApiOperation(value = "Trades LIST (get)", notes = "VIEW - Return all Trades list.")
    @GetMapping("/list")
    public String home(final Model model) {
        model.addAttribute("trade", tradeService.findAllTrade());
        LOGGER.info("GET request SUCCESS for: /trade/list");
        return "trade/list";
    }

    /**
     * Get HTML page used to add a new trade.
     *
     * @param model
     * @return /trade/add.html page
     */
    @ApiOperation(value = "ADD Trade (get)", notes = "VIEW - Add new Trade")
    @GetMapping("/add")
    public String addUser(final Model model) {
        model.addAttribute("trade", new Trade());
        LOGGER.info("GET request SUCCESS for: /trade/add");
        return "trade/add";
    }

    /**
     * Post HTML page used to validate a new trade.
     *
     * @param trade
     * @param result
     * @param model
     * @return /trade/add.html page if bad request or else /trade/list
     */
    @ApiOperation(value = "VALIDATE Trade (post)", notes = "VIEW - Validate / save the new Trade")
    @PostMapping("/validate")
    public String validate(@Valid final Trade trade, final BindingResult result,
            final Model model) {

        if (!result.hasErrors()) {
            Trade tradeResult = tradeService.saveTrade(trade);
            if (tradeResult != null) {
                model.addAttribute("trade", tradeResult);
                LOGGER.info("POST request SUCCESS for: /trade/validate");
                return "redirect:/trade/list";
            }
        }
        LOGGER.info("POST request FAILED for: /trade/validate");
        return "trade/add";
    }

    /**
     * Get HTML page used to update a trade.
     *
     * @param id
     * @param model
     * @return /trade/update.html page
     */
    @ApiOperation(value = "UPDATE Trade (get)", notes = "VIEW - Get a Trade by id and retrieve to update it.")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
            final Model model) {
        Trade trade = tradeService.getTradeById(id);
        model.addAttribute("trade", trade);
        LOGGER.info("GET request SUCCESS for: /trade/update/{id}");
        return "trade/update";
    }

    /**
     * Post HTML page used to update a trade.
     *
     * @param tradeId
     * @param trade
     * @param result
     * @param model
     * @return /trade/update.html page if bad request or else /trade/list
     */
    @ApiOperation(value = "UPDATE Trade (post)", notes = "VIEW - Update the Trade.")
    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") final Integer tradeId,
            @Valid final Trade trade, final BindingResult result,
            final Model model) {

        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /trade/update/{id}");
            return "trade/update/" + tradeId;
        }
        trade.setTradeId(tradeId);
        boolean isUpdated = tradeService.updateTrade(tradeId, trade);
        if (isUpdated) {
            model.addAttribute("trade", tradeService.findAllTrade());
            LOGGER.info("POST request SUCCESS for: /trade/update/{id}");
            return "redirect:/trade/list";
        }
        LOGGER.info("POST request FAILED for: /trade/validate");
        return "trade/update";
    }

    /**
     * Get HTML page used to delete a trade.
     *
     * @param tradeId
     * @param model
     * @return /trade/list.html page
     */
    @ApiOperation(value = "DELETE Trade (get)", notes = "VIEW - Get Trade with his id, and delete it.")
    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") final Integer tradeId,
            final Model model) {

        boolean isDeleted = tradeService.deleteTrade(tradeId);
        if (isDeleted) {
            model.addAttribute("trade", tradeService.findAllTrade());
            LOGGER.info("GET request SUCCESS for: /trade/delete/{id}");
        } else {
            LOGGER.info("GET request FAILED for: /trade/delete/{id}");
        }
        return "redirect:/trade/list";
    }
}
