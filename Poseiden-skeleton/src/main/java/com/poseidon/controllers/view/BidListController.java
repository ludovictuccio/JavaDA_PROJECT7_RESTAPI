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

import com.poseidon.domain.BidList;
import com.poseidon.repositories.BidListRepository;
import com.poseidon.services.BidListService;

@Controller
public class BidListController {

    private static final Logger LOGGER = LogManager
            .getLogger("BidListController");

    @Autowired
    private BidListService bidListService;

    @Autowired
    private BidListRepository bidListRepository;

    /**
     * Get HTML page used to display all bid list.
     *
     * @param model
     * @return /bidList/list.html page
     */
    @GetMapping("/bidList/list")
    public String home(final Model model) {
        model.addAttribute("bidList", bidListService.findAllBids());
        LOGGER.info("GET request SUCCESS for: /bidList/list");
        return "bidList/list";
    }

    /**
     * Get HTML page used to add a new bid list.
     *
     * @param model
     * @return /bidList/add.html page
     */
    @GetMapping("/bidList/add")
    public String addBidForm(final Model model) {
        model.addAttribute("bidList", new BidList());
        LOGGER.info("GET request SUCCESS for: /bidList/add");
        return "bidList/add";
    }

    /**
     * Post HTML page used to validate a new bid list.
     *
     * @param bid
     * @param result
     * @param model
     * @return /bidList/add.html page if bad request or else /bidList/list
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid final BidList bid, final BindingResult result,
            final Model model) {

        if (!result.hasErrors()) {
            BidList bidList = bidListService.saveBid(bid.getAccount(),
                    bid.getType(), bid.getBidQuantity());
            model.addAttribute("bidList", bidList);
            LOGGER.info("POST request SUCCESS for: /bidList/validate");
            return "redirect:/bidList/list";
        }
        LOGGER.info("POST request FAILED for: /bidList/validate");
        return "bidList/add";
    }

    /**
     * Get HTML page used to update a bid list.
     *
     * @param id
     * @param model
     * @return /bidList/update.html page
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
            final Model model) {

        BidList bidList = bidListService.getBidById(id);
        model.addAttribute("bidList", bidList);
        LOGGER.info("GET request SUCCESS for: /bidList/update/{id}");
        return "bidList/update";
    }

    /**
     * Post HTML page used to update a bid list.
     *
     * @param id
     * @param bidList
     * @param result
     * @param model
     * @return /bidList/update.html page if bad request or else /bidList/list
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") final Integer id,
            @Valid final BidList bidList, final BindingResult result,
            final Model model) {

        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /bidList/update/{id}");
            return "bidList/update/" + id;
        }
        bidList.setBidListId(id);
        bidListRepository.save(bidList);
        model.addAttribute("bidlist", bidListService.findAllBids());
        LOGGER.info("POST request SUCCESS for: /bidList/update/{id}");
        return "redirect:/bidList/list";
    }

    /**
     * Get HTML page used to delete a bid list.
     *
     * @param id
     * @param model
     * @return /bidList/list.html page
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") final Integer id,
            final Model model) {
        BidList bidList = bidListService.getBidById(id);
        bidListService.deleteBid(id, bidList.getAccount());
        model.addAttribute("bidlist", bidListService.findAllBids());
        LOGGER.info("GET request SUCCESS for: /bidList/delete/{id}");
        return "redirect:/bidList/list";
    }
}
