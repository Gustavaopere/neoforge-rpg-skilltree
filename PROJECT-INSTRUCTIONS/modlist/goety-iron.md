# Goety Iron — 3.1

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db8173ac69f454c21e3d95  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** Goety Iron
- **Arquivo JAR:** `GoetyIron-1.21.1-NeoForge-3.1.jar`
- **Versão 1.21.1:** `3.1`
- **Categoria:** Compat; Magia; RPG
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/goety-iron/files/8662179
- **Função:** Bridge Goety↔Iron's que adapta mobs mágicos do Iron's como servants do Goety e adiciona configuração/compatibilidade para summons e spell attributes desses servos.
- **Dependências:** Goety 3.1.4 + Iron's Spells 'n Spellbooks 1.21.1-3.16.3, ambos presentes fisicamente.
- **Compatibilidade/Riscos:** Riscos: summon replacement duplicado, owner/friendly-fire incorreto, spell attributes aplicados por múltiplos addons, config drift, Void Vault reset duplicado, servant stale após lifecycle e version drift Goety/Iron's. 3.1 corrige configs e allied Ominous Fire Orbs.
- **Sobreposição:** Bridge específica entre Goety e Iron's; não substitui nenhum provider. Goety mantém servant ownership/lifecycle e Iron's mantém spells/atributos/entidades-base.
- **Observações:** Release oficial 3.1 de 16/08/2026. Adiciona atributos de spell configuráveis por servant, reset de Goety Void Vault via Tincture of Forgetfulness e replacements configuráveis de Polar Bear/Vex summons.
- **Procedência:** modlist.txt física atual de 09/09/2026 + release/changelog oficial Goety Iron 3.1. Source exato não obtido; internals e nomes/defaults de config permanecem fail-closed.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Goety Iron 3.1 release-pinned; servant bridge, per-servant spell attributes, summon replacements, allied projectiles, Void Vault reset, lifecycle, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `GoetyIron-1.21.1-NeoForge-3.1.jar`, mod id `goetyiron`, versão `3.1`. A release oficial 3.1 para NeoForge 1.21.1 é exata e o changelog correspondente foi auditado. O mod conecta **Goety 3.1.4** a **Iron's Spells 'n Spellbooks 1.21.1-3.16.3**; não é um terceiro sistema de magia independente.

## 1. Papel e authority
Goety Iron transforma/integra criaturas mágicas do Iron's ao modelo de **servants comandáveis do Goety** e adiciona configurações/adaptações para esses servants.
- Goety continua authority de owner, servant command/lifecycle e Soul Energy quando aplicável;
- Iron's continua authority dos spells, atributos e comportamento-base das entidades que fornece;
- Goety Iron é authority da bridge, substituições de summons e configuração específica de seus servants.

## 2. Dependências físicas
- `goety-3.1.4.jar`;
- `irons_spellbooks-1.21.1-3.16.3.jar`;
- `GoetyIron-1.21.1-NeoForge-3.1.jar`.
Essa combinação deve ser tratada como matriz de compatibilidade concreta do pack.

## 3. Release 3.1 — correções
O changelog exato 3.1 registra:
- correção de opções de configuração que não produziam efeito;
- correção de **Improved Ominous Fire Orbs** que podiam ser destruídos por mobs aliados;
- correções de compatibilidade com alguns mods.
Esses casos são regression gates diretos da build instalada.

## 4. Spell attributes por servant
A 3.1 adiciona **opções de configuração de atributos de spell para cada servant**. Isso cria uma superfície de balanceamento explícita: atributos devem alterar o desempenho da entidade serva pelo caminho suportado pelo addon/Iron's, não por patches paralelos que disputem os mesmos valores.

## 5. Tincture of Forgetfulness e Void Vault
A release 3.1 adiciona à **Tincture of Forgetfulness** capacidade de resetar o **Void Vault do Goety**. Essa ação é stateful e deve ser server-authoritative e exactly-once; reconnect ou uso duplicado não pode executar o reset duas vezes por uma única ação do jogador.

## 6. Substituição de summons
A 3.1 adiciona dois fluxos configuráveis:
- Polar Bears invocados podem ser substituídos por **Polar Bear Servants**;
- Vexes invocados podem ser substituídos por **Vex Servants**.
Ambas as substituições podem ser desativadas em config. O evento causal de summon precisa resultar em uma única entidade final, sem manter simultaneamente summon original + servant substituto.

## 7. Friendly-fire / projectile ownership
O fix de Ominous Fire Orbs mostra que allied-mob filtering e ownership são superfícies reais. Projetis/attacks de servants devem reconhecer aliança/owner de forma coerente com Goety e Iron's. Outros systems de party/team não devem ser assumidos equivalentes sem bridge explícita.

## 8. Configuração
A release comprova configuração de spell attributes por servant e toggles para substituição de Polar Bear/Vex. Nomes de chaves, defaults e ranges não são inventados sem config/source exato. O arquivo efetivamente gerado na instância deve ser capturado antes de tuning automatizado.

## 9. Client / server
A distribuição é Client & Server.
- **Servidor:** summon replacement, owner/ally checks, servant stats, spell effects e Void Vault reset.
- **Cliente:** render/animation/particles/sounds e apresentação.
Client prediction ou animação não deve criar/destruir entidade authoritative.

## 10. Lifecycle
Validar summon, replacement, owner disconnect, death/respawn, dimension transfer, servant death/despawn, server restart e config reload/restart. Uma entidade substituída não deve ressurgir como original após chunk reload nem deixar UUID/reference stale.

## 11. Multiplayer
Dois jogadores invocando ao mesmo tempo precisam receber servants vinculados ao owner correto. Friendly fire, target selection e projectiles não podem usar cache global de owner. Void Vault reset deve atingir apenas o contexto previsto pelo item/Goety.

## 12. Integrações no pack
Goety Iron fica no cruzamento de dois stacks grandes:
- **Goety 3.1.4:** servants e state mágico provider-native;
- **Iron's 3.16.3:** spells/attributes/entities mágicas;
- outros addons de Iron's podem alterar spell attributes ou summons, exigindo smoke test quando atuarem sobre as mesmas entidades.
Não tratar coexistência com outros addons como compatibilidade automática.

## 13. Riscos técnicos
- summon original e servant substituto coexistirem;
- owner attribution incorreta;
- spell attribute configurado duas vezes por addon externo;
- config toggle não aplicado — regressão já corrigida em 3.1;
- allied projectile ser destruído/hostilizado incorretamente;
- Void Vault reset duplicado ou aplicado ao jogador errado;
- version drift Goety/Iron's;
- servant UUID/state stale após restart/dimension change;
- client/server mismatch ou registry mismatch.

## 14. Matriz de testes obrigatória
- [ ] Dedicated server boot com Goety 3.1.4 + Iron's 3.16.3 + Goety Iron 3.1.
- [ ] Cada config relevante realmente altera comportamento.
- [ ] Polar Bear summon replacement on/off produz exatamente uma entidade final.
- [ ] Vex summon replacement on/off produz exatamente uma entidade final.
- [ ] Spell attributes por servant mudam apenas o servant esperado.
- [ ] Improved Ominous Fire Orb não é destruída por aliado indevidamente.
- [ ] Friendly-fire/owner checks com dois jogadores e múltiplos servants.
- [ ] Tincture of Forgetfulness reseta Void Vault uma vez e no owner correto.
- [ ] Reconnect, death e dimension change sem orphan/stale servant state.
- [ ] Restart do servidor preserva apenas state intencional.
- [ ] Outros addons Iron's instalados não duplicam attribute/summon hooks.

## 15. Evidências e limites
- **Modlist física:** Goety Iron 3.1, Goety 3.1.4 e Iron's Spells 3.16.3.
- **Release oficial:** `GoetyIron-1.21.1-NeoForge-3.1.jar`, publicada 16/08/2026, Client & Server.
- **Changelog 3.1:** config fixes, allied Ominous Fire Orb fix, compatibility fixes, per-servant spell attributes, Tincture/Void Vault reset e Polar Bear/Vex replacements.
- **Limite:** source exato da 3.1 não foi obtido nesta auditoria; classes, registry IDs e nomes/defaults de config não são inventados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
