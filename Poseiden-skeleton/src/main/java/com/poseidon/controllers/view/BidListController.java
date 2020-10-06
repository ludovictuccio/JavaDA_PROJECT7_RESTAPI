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
import com.poseidon.services.BidListService;

@Controller
public class BidListController {
    // TODO: Inject Bid service

    private static final Logger LOGGER = LogManager
            .getLogger("BidListController");

    @Autowired
    private BidListService bidListService;

    @GetMapping("/api//bidList/list")
    public String home(final Model model) {
        // TODO: call service find all bids to show to the view

        model.addAttribute("bidList", bidListService.findAllBids());
        return "bidList/list";
    }

    @GetMapping("/api//bidList/add")
    public String addBidForm(final BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/api//bidList/validate")
    public String validate(@Valid final BidList bid, final BindingResult result,
            Model model) {
        // TODO: check data valid and save to db, after saving return bid list

        if (!result.hasErrors()) {
            bidListService.saveBid(bid.getAccount(), bid.getType(),
                    bid.getBidQuantity());
            model.addAttribute("bidList", bidListService.findAllBids());

            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/api//bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
            final Model model) {
        // TODO: get Bid by Id and to model then show to the form

        BidList bidList = bidListService.getBidById(id);
        model.addAttribute("bidList", bidList);

        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") final Integer id,
            @Valid BidList bidList, BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and
        // return list Bid

        if (result.hasErrors()) {
            return "bidList/update/" + id;
        }
        bidList.setBidListId(id);
        bidListService.saveBid(bidList.getAccount(), bidList.getType(),
                bidList.getBidQuantity());
        model.addAttribute("bidlist", bidListService.findAllBids());

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") final Integer id,
            final Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        BidList bidList = bidListService.getBidById(id);
        bidListService.deleteBid(id, bidList.getAccount());
        model.addAttribute("bidlist", bidListService.findAllBids());

        return "redirect:/bidList/list";
    }
}
