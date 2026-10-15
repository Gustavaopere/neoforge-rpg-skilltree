# Stage 03 / Ponto 0 — Matriz de QA físico do modpack

- **Status:** roteiro preparado, **NÃO EXECUTADO**; não constitui certificação.
- **Base automatizada verificada:** PR #920, HEAD `d68e041150460deeabf7ea5e4d4b536d4703b8d8`, 26/26 workflows GitHub Actions `success` e Sonar New Code Coverage 80,8% (mínimo 80%), zero issues novos e hotspots.
- **Ambiente-alvo:** Minecraft **1.21.1**, **NeoForge 1.21.1** (versão física da instância a registrar), **Java 21**, JAR físico do PR e **mesma modlist real** que se pretende distribuir, não substituí-la apenas por snapshots de CI.
- **Responsável/registro:** preencher para cada caso data, SHA do JAR, hashes dos mods relevantes, versão Java/NeoForge, modo singleplayer ou dedicado, jogadores A/B, coordenadas e seed se mundo alterado, passos, resultado esperado/observado, log e screenshot/vídeo de evidência. Evidência ausente significa **PENDENTE**, nunca PASS.

## Preparação da sessão

1. Guardar backup separado de mundo/instância e das configurações. Confirmar hashes do JAR e da modlist instalada; testar primeiro mundo descartável.
2. Construir o artefato exato do SHA acima; conferir que não há JAR antigo ou duplicado no diretório `mods`. Usar conta A e conta B distintas num servidor dedicado e, quando indicado, cliente individual com os mesmos mods.
3. Usar as operações de investimento/respec **oferecidas pelo Core no jogo**, capturando ranks canônicos visíveis ou snapshot de logs. **Não** introduzir cheats/commands fictícios para conceder rank; se o fluxo de atribuição ainda estiver ausente na UI, registrar o teste como **BLOCKED — aquisição de ranks**, não simular dados sem rastreio.
4. Capturar baseline sem investimento (rank 0), primeiro marco e rank alto; executar reload/relog quando indicado. Os seis atributos são Força, Constituição, Agilidade, Inteligência, Determinação e Carisma. Cada rank continua custando 1 Core Point conforme policy atual; não presumir UI final.

## Casos obrigatórios de aceitação

| ID | Cenário verificável | Resultado esperado |
|---|---|---|
| QA-00 | Rank 0, rank perto/antes do primeiro marco, primeiro marco, rank 30, 300, respec, morte e reconexão; abrir mesmo mundo/servidor de novo | Bonuses reaplicados a partir de **CoreProgressionState** sem drift, duplicação nem pontos extras; rank 0 = 0 |
| QA-01 | Determinação 0/7/8/16; projétil de **shulker vanilla** atingindo jogador survival A; medir duração Levitação em ticks com logger/overlay de diagnóstico | Antes do marco (rank 0/7) duração vanilla; após marco, encurtamento conforme `floor(duration×(1-bônus))`, mínimo 1, sem desaparecer imediatamente |
| QA-02 | ShulkerBullet órfão / levitação adicionada por comando / outro mob ou provider | **Nenhum** encurtamento por esse handler sem autoria shulker vanilla comprovada |
| QA-03 | Reaplicar/renovar Levitação no meio do efeito; fazer respec antes do corte; trocar para criativo/espectador; logout/login e parar/reabrir servidor | Não remover cedo uma instância alterada nem manter recibo obsoleto; sem buffs em fake players ou durações infinitas |
| QA-04 | Determinação: efeito de controle não Levitação (lentidão, cansaço, efeitos de bosses/spells) | Sem aplicação do bônus limitado de Shulker; não afirmar resistência genérica |
| QA-05 | Carisma rank 0/9/10/30/300: usar magia **Holy** elegível do Iron's 1.21.1-3.16.3 para curar outro jogador B, com vida faltante suficiente; medir saúde real e comparar com cura base | Bônus só em cura **atribuída** a A e destinada a B; rank 10 +0,4%, teto da contribuição +10%; nunca ultrapassar vida máxima |
| QA-06 | Autocura, regeneração natural, cura por item/terceiros, escola não Holy; curas idênticas no mesmo tick ao mesmo alvo entre providers; PVP/equipes | Nenhuma cura sem autoria válida amplificada; **colisão no mesmo tick exige observação explícita** antes de liberar multiplayer |
| QA-07 | Carisma: lobo vanilla domesticado de A atacando mob hostil; lobo de B, lobo selvagem, alvos não hostis, dano indireto e respec | Bônus só para o domínio declarado de companheiro próprio; nunca global para outros pets/summons |
| QA-08 | Constituição: cura/regeneração, fogo contínuo sem entidade atacante, Wither vanilla sem atacante, lava, veneno e magia; sob efeitos de diversos mods | Only fontes vanilla explicitamente cobertas por adapter; DoT não se estende a lava/veneno/magia sem auditoria |
| QA-09 | Força, Agilidade, Inteligência e stamina/cooldown de Determinação com Epic Fight, Iron's, Create e demais providers presentes/ausentes | Modifiers corretos no provider exato; remover no respec; reentrância não compõe duas vezes; mod ausente não impede o servidor |
| QA-10 | Aldeões vanilla: jogador A com Carisma alto, B rank 0, compras e vendas, gossip, Hero of the Village e restock | **Nenhum desconto do mod está implementado**; preços vanilla preservados e sem oferta de custo zero, compras grátis, lucro por repetição ou preço compartilhado alterado |
| QA-11 | Cliente A, cliente B e servidor dedicado, vários ciclos de /reload do datapack, reconnect e restart do servidor | Sem crash, desync de UI, drift de modificadores, perda de ranks ou mudança permanente de preços; registrar casos com mods ausentes |

## Critérios para sair de Draft

- Cada caso aplicável acima deve ter registro PASS com evidência; BLOCKED ou FAIL exigem correção e novo teste. Nenhum teste automatizado substitui os casos simultâneos com dois jogadores reais.
- Qualquer novo commit requer **novamente** CI e Sonar para o SHA exato, mantendo mínimo **80% de cobertura em código novo**, 0 falha de workflow e revisão dos alerts.
- A pendência do desconto `CHARISMA_VILLAGER_DISCOUNT` **não** pode ser considerada implementada apenas por cálculo da fórmula, troca pós-fato ou mudança global em ofertas; precisa de contrato de transação por jogador e QA de não exploração. Decidir explicitamente se ficará em um incremento posterior antes de marcar o Ponto 0 como final.
- PR #920 deve permanecer **Draft/sem merge** até decisão de escopo, QA físico e aprovação para release.
