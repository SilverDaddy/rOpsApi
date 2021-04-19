package infra.rOpsApi.admin.bot.controller;

import infra.rOpsApi.bot.model.Bot;
import infra.rOpsApi.bot.repository.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/bots")
public class BotAdminController {
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

    @PostMapping
    public Bot insertBot(@RequestBody Bot param) {
        return botRepository.save(new Bot(param.getBot_id(), param.getBot_name(), param.getDescription()));
    }

    @PatchMapping("/{bot_id}")
    public Bot updateBot(@PathVariable("bot_id") String id, @RequestBody Bot param) {
        Bot bot = botRepository.findById(id).get();
        bot.setBot_name(param.getBot_name());
        bot.setDescription(param.getDescription());
        return botRepository.save(bot);
    }

    @DeleteMapping("/{bot_id}")
    public void deleteBot(@PathVariable("bot_id") String id) {
        botRepository.deleteById(id);
    }
}