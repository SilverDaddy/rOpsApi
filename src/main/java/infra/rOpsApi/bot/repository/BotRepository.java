package infra.rOpsApi.bot.repository;

import infra.rOpsApi.bot.model.Bot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotRepository extends JpaRepository<Bot, String> {

}