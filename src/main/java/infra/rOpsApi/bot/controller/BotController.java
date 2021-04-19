package infra.rOpsApi.bot.controller;

import infra.rOpsApi.bot.model.Bot;
import infra.rOpsApi.bot.repository.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bots")
public class BotController {
    @Autowired
    private BotRepository botRepository;

    @GetMapping
    public List<Bot> getBots() {
        return botRepository.findAll();
    }

    @GetMapping("/{bot_id}")
    public Bot getBotById(@PathVariable("bot_id") String id) {
        return botRepository.findById(id).get();
    }
}