# Vampire Spells Addon

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#556**: JAR `vampire_spells_addon-neoforge-1.21.1-0.0.9.jar`, mod id `vampire_spells_addon`, runtime `1.21.1-0.0.9`, SHA-1 `004eec424ea7ba808b020d012932bdee4cdf3756`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Vampire Spells Addon
- **Arquivo JAR:** `vampire_spells_addon-neoforge-1.21.1-0.0.9.jar`
- **Versão 1.21.1:** 1.21.1-0.0.9
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** 
- **Categoria:** Magia, Compat, RPG
- **Função:** Bridge Vampirism ↔ Iron's Spells: adapta custo de Blood spells para blood/mana, restaura blood por dano real de Ray of Siphoning/Devour e converte Holy healing/utility em consequências apropriadas para vampiros.
- **Dependências:** NeoForge \>=21.1.200 e \<21.2; Vampirism \>=1.10.7 e \<1.11; Iron's Spells \>=1.21.1-3.14.3 e \<4. Pack atual: NeoForge 21.1.250, Vampirism 1.10.13, Iron's 3.16.3.
- **Sobreposição:** Não cria escola/facção. É tradução entre Blood/Holy do Iron's e fisiologia/blood do Vampirism; qualquer outra bridge nesse mesmo boundary precisa de exactly-once.
- **Compatibilidade/Riscos:** Faixas suportadas atendidas. Riscos: double payment mana+blood, double payout por damage listeners, Holy healing reaplicado por outra bridge, cooldown stacking e correlação stale após cancel/relog/death. Serverconfig real do mundo ainda precisa de QA.
- **Observações:** Mod id `vampire_spells_addon`; runtime `1.21.1-0.0.9`. Vampirism é authority de blood/faction e Iron's de spell/mana/cooldown. Provider-specific fallback deve ser fail-closed.
- **Procedência:** modlist(1).txt física atual de 27/09/2026 + Vampire Spells Addon 1.21.1-0.0.9 + NeoForge 21.1.250 + Vampirism 1.10.13 + Iron's Spells 3.16.3. As faixas publicadas continuam satisfeitas. Histórico 08/09–13/09 com NeoForge 21.1.248 preservado; serverconfig e runtime QA não foram executados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/vampirism-irons-spells-compatibility
- **Atualização/Status:** RECONCILIADO EM 27/09/2026 — Vampire Spells Addon 0.0.9 permanece instalado; faixa NeoForge continua satisfeita pelo loader físico atual 21.1.250. Vampirism 1.10.13 e Iron's Spells 3.16.3 permanecem os providers atuais; pagamento atômico, Blood payout, Holy inversion, riscos e testes preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `vampire_spells_addon-neoforge-1.21.1-0.0.9.jar`, mod id `vampire_spells_addon`, runtime `1.21.1-0.0.9`. Esta é uma **bridge Vampirism ↔ Iron's Spells**, não uma nova escola ou facção. Vampirism continua authority de blood/faction; Iron's continua authority de spell, mana, cooldown e resolução do cast.
## 1. Identidade e faixa suportada
- **Build:** 0.0.9, NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Config por mundo:** `serverconfig/vampire_spells_addon-server.toml`.
- **Vampirism suportado:** `>=1.10.7` e `<1.11`.
- **Iron's suportado:** `>=1.21.1-3.14.3` e `<4`.
- **NeoForge:** `>=21.1.200` e `<21.2`.
O pack atual satisfaz essas faixas com **NeoForge 21.1.250**, **Vampirism 1.10.13** e **Iron's Spells 3.16.3**.
## 2. Blood School — custo de recurso
Para spells Blood com custo de mana, a bridge altera a economia especificamente para casters vampiros.
O comportamento documentado é:
- por padrão, tenta pagar o cast com mana;
- quando o custo integral de mana não pode ser coberto, spells elegíveis podem cair para um pagamento **atômico** em blood;
- o fallback não deve gastar mana parcialmente e depois blood pelo restante;
- configuração pode alterar a preferência/uso de blood.
Não criar um terceiro recurso nem espelhar blood em attachment próprio. A mutação deve ocorrer no provider Vampirism e o cast continua pertencendo ao pipeline Iron's.
## 3. Ray of Siphoning e Devour
A bridge permite que **Ray of Siphoning** e **Devour** restaurem blood ao vampiro com base no **dano de vida efetivamente entregue** ao alvo após processamento de dano/absorção.
O projeto também limita o ganho para não contar overkill além da vida disponível e não solicitar blood além da capacidade do caster.
Boundary de causalidade:
- um hit/cast causal = uma liquidação de blood;
- não contabilizar dano visual, tentado ou cancelado;
- não aplicar segundo payout em RPG Skill Tree/Black Arcana;
- recasts/efeitos derivados só geram nova consequência se o provider realmente os resolver como novo dano elegível.
## 4. Holy School contra vampiros
A bridge adapta Holy para a fisiologia do Vampirism:
- healing Holy pode **ferir vampiros em vez de curá-los** e suprimir a cura normal;
- Holy utility spells usados por caster vampiro podem causar dano no próprio caster e cancelar o cast conforme configuração/contrato;
- targeting state é limpo no fluxo de cancelamento documentado.
Isso não torna “Holy = dano universal contra qualquer undead”. O escopo auditado é a tradução específica para entidades/jogadores reconhecidos pelo Vampirism.
## 5. Cooldowns e configuração
O addon também possui regras configuráveis para cooldowns de Blood spells de vampiros. Qualquer perk que modifique cooldown deve compor com o valor final provider-native e impedir multiplicação duplicada.
Não fixar valores numéricos de config no código próprio sem ler o `serverconfig` efetivo do mundo.
## 6. Arquitetura de integração
O projeto evita acoplamento compile-time amplo aos pais e carrega/adapta APIs em runtime com adapters/reflection e mixins estreitos onde necessário.
Para mods próprios, isso reforça o padrão:
- não importar classes opcionais em common code sem guard;
- verificar presença/faixa de versão;
- preferir hook/adapter real;
- se a integração não puder ser inicializada, falhar fechado e deixar Vampirism/Iron's operarem nativamente.
## 7. Server authority, deduplicação e lifecycle
Blood, mana, health damage, cooldown e cast são gameplay state e devem ser server-authoritative.
O addon mantém estado de correlação para associar fases do mesmo cast/dano; esse estado é bounded e limpo em boundaries de lifecycle.
Testes obrigatórios para integração externa:
- login/relog;
- death/respawn;
- dimension change;
- cast cancelado;
- dano absorvido/mitigado;
- target morrendo por overkill;
- dois alvos/dois jogadores;
- mana exatamente suficiente/insuficiente;
- interrupção entre pré-cast e resolução.
## 8. Boundaries para RPG Skill Tree
- Não duplicar barra de blood nem conversão mana↔blood.
- Não conceder Mastery por gastar blood por tick ou manter estado vampírico.
- Cast válido pode alimentar progressão apenas quando houver autoria causal e deduplicação do cast real.
- Blood restaurado por Siphoning/Devour é efeito provider-native; não premiar novamente pelo mesmo payout.
- Holy self-damage/cancel não deve ser convertido silenciosamente em bônus genérico quando a bridge estiver ausente.
- Provider-specific perk sem hook comprovado = **FAIL-CLOSED**.
## 9. Riscos
1. **Double payment:** mana e blood cobrados no mesmo cast por bridge concorrente.
2. **Double payout:** dano observado por mais de um listener restaurando blood duas vezes.
3. **Holy heal inversion:** outro healing mod reaplicar cura depois do veto/dano.
4. **Cooldown stacking:** multiplicadores de Iron's, addon e perks compondo de forma incorreta.
5. **Lifecycle correlation:** estado de cast stale após cancel/relog/death.
6. **Version drift:** faixas atuais são atendidas, mas update futuro de Vampirism `1.11+` ou Iron's `4+` exige reauditoria.
## 10. Matriz de testes
- [ ] Dedicated server inicia com Vampirism 1.10.13 + Iron's 3.16.3 + addon 0.0.9.
- [ ] Blood spell com mana suficiente paga apenas mana conforme config atual.
- [ ] Mana insuficiente aciona fallback atômico sem cobrança parcial duplicada.
- [ ] Falta de ambos os recursos cancela sem mutation parcial.
- [ ] Ray of Siphoning restaura blood proporcional ao dano real e respeita capacidade.
- [ ] Devour segue a mesma regra de dano real/overkill.
- [ ] Holy healing em vampiro não cura e aplica a consequência configurada uma única vez.
- [ ] Holy utility em caster vampiro cancela/causa dano conforme provider sem state stale.
- [ ] Cooldown final não é aplicado duas vezes.
- [ ] Relog/death/dimension change limpa correlação pendente.
- [ ] Multiplayer com casts simultâneos não mistura correlações entre jogadores.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 11. Evidências
- **Modlist física 08/09/2026:** addon 0.0.9; Vampirism 1.10.13; Iron's 3.16.3; NeoForge 21.1.248.
- CurseForge/GitHub oficiais do addon: ranges suportados, serverconfig, Blood fallback, Siphoning/Devour, Holy handling e arquitetura de integração.
## 12. Limitação
A configuração efetiva do mundo e os resultados runtime ainda não foram lidos/executados nesta etapa. Valores finais de custo, cooldown e consequências permanecem dependentes do serverconfig real.
## 13. Revalidação física — 11/09/2026
O runtime físico continua exatamente `vampire_spells_addon-neoforge-1.21.1-0.0.9.jar`, mod id `vampire_spells_addon`, versão `1.21.1-0.0.9`. A release 0.0.9 permanece a linha 1.21.1 pertinente.
As faixas declaradas continuam satisfeitas por NeoForge `21.1.248`, Vampirism `1.10.13` e Iron's Spells `3.16.3`. Nenhum serverconfig efetivo, fallback mana→blood, payout de Siphoning/Devour, Holy inversion, cooldown ou lifecycle de correlação foi testado nesta recatalogação.
## 14. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `vampire_spells_addon-neoforge-1.21.1-0.0.9.jar`, versão `1.21.1-0.0.9`, e a página oficial continua listando `0.0.9` como a release NeoForge 1.21.1 mais recente. As faixas publicadas seguem atendidas por NeoForge `21.1.248`, Vampirism `1.10.13` e Iron's Spells `3.16.3`. O contrato de fallback atômico mana→blood, payout por dano real em Ray of Siphoning/Devour, Holy inversion e limpeza de estado de correlação permanece documentado. Nenhum serverconfig efetivo, payout, Holy handling, cooldown ou lifecycle de correlação foi testado nesta revalidação.
## 15. Reconciliação física — 27/09/2026
A autoridade física atual mantém `vampire_spells_addon-neoforge-1.21.1-0.0.9.jar`, mod id `vampire_spells_addon`, runtime `1.21.1-0.0.9`. O modloader físico atual é `neoforge-21.1.250`, enquanto Vampirism `1.10.13` e Iron's Spells `3.16.3` permanecem os providers instalados. A faixa declarada `NeoForge >=21.1.200 e <21.2` continua satisfeita. As referências de 08/09–13/09 a NeoForge 21.1.248 foram mantidas como snapshots históricos. Nenhum serverconfig efetivo, fallback mana→blood, payout, Holy handling, cooldown ou lifecycle de correlação foi testado nesta reconciliação.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Vampirism Iron's Spells Compatibility
- **Último candidato 1.21.1 / NeoForge localizado:** **0.0.10 (Release, 17/10)**
- **Estado:** **CANDIDATO PENDENTE**
- **Gate de promoção:** Compat entre Vampirism e Iron's Spells: validar spell ownership/costs, vampire state, attributes, damage/healing, sync e multiplayer.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/vampirism-irons-spells-compatibility

## Auditoria da release NeoForge 1.21.1 — 23/10/2026 (0.0.9 → 0.0.10)

**Inventário físico histórico (inalterado):** posição **#556**, `vampire_spells_addon-neoforge-1.21.1-0.0.9.jar`, mod id `vampire_spells_addon`, runtime `1.21.1-0.0.9`. **Projeto oficial do JAR:** *Vampirism Iron's Spells Compatibility*, CurseForge **1350048**, autor `xsharov_`, categorias temáticas oficiais **Addons + Adventure and RPG + Food + Magic**; correspondem ao diretório utilizado. **Última Release verificada para NeoForge 1.21.1:** **0.0.10**, publicada **17/10/2026**, JAR `vampire_spells_addon-neoforge-1.21.1-0.0.10.jar`, File ID **9226160**. O intervalo publicado pertinente ao mesmo loader/jogo é **0.0.9 → 0.0.10**, sem outro artefato intermediário confirmado.

### 0.0.10 — changelog do JAR correto e correlação com código-fonte

O [changelog da release NeoForge exata](https://www.curseforge.com/minecraft/mc-mods/vampirism-irons-spells-compatibility/files/9226160) declara a adição de **configurações para dano de Holy healing e blood casting por escola de magia**. Para discriminar o comportamento efetivo, o source do **mesmo desenvolvedor**, `xsharov/VampireSpellsAddon`, contém commits de 17/10 mesclados na PR #9 (merge `d76dba6bf7f4b5451701e1e727bd662a2b0201c1`); trata-se de comprovação de mudanças na janela de publicação, **sem inspeção de bytecode do JAR**.

**1. Config `bloodCastingSchools`: escolha das escolas que permitem pagar mana com sangue.**
- A seleção deixa de ser obrigatoriamente exclusiva da **Blood School**; a **config padrão mantém somente Blood School**, preservando o comportamento esperado da 0.0.9 enquanto nenhuma alteração manual é feita.
- As entradas aceitam ID completo (`irons_spellbooks:fire`), nome simples do Iron's (`fire`, interpretado no namespace `irons_spellbooks`) ou wildcard `*` para **todas as escolas**, inclusive de addons; lista vazia `[]` **desliga completamente a substituição mana→blood**.
- `alwaysUseBloodForVampireBloodSpells` passa a valer para **todas as escolas selecionadas**, não somente Blood; o custo é atômico, sem cobrança parcial de mana mais sangue no mesmo cast.
- Permanecem excluídos da substituição **Ray of Siphoning**, magias Holy utility cuja execução pelo vampiro é bloqueada, jogadores em Creative, casts sem custo de mana e recasts upstream.
- **Fonte do source e testes de parser:** https://github.com/xsharov/VampireSpellsAddon/commit/ab54946d7590e611f4dd3e64fc5c7e2c9a58bcee .

**2. Config `disableHolyHealDamage` — default `false`.**
- Por padrão, mantém a tradução de **Holy healing para dano a vampiros** já descrita neste dossier, suprimindo a cura correspondente.
- Quando configurada em `true`, as magias Holy healing **voltam a curar vampiros normalmente**, sem dano ao vampiro caster nem ao alvo devido a esse handler.
- **A configuração NÃO desliga** reflexão de dano Holy, dano Holy duplicado em NPCs vampiros nem penalidade/cancelamento de **Holy utility**; esses fluxos são separados no código.
- O source cita Heal, Greater Heal, Healing Circle, Blessing of Life e Cloud of Regeneration como exemplos de curas cobertas. Fonte: https://github.com/xsharov/VampireSpellsAddon/commit/393bd9505287d327990f631471fbed658a1dadaa .

**3. Devour sob escolas selecionáveis e casts contínuos — ajuste do mesmo lote de source.**
- Quando Blood School não integra `bloodCastingSchools`, o Devour **não pode usar o fallback de blood**: o pre-cast exige mana suficiente para o **preço multiplicado específico de vampiro**, e em caso contrário cancela com notificação de mana insuficiente.
- O README registra uma limitação na execução de **casts contínuos**: no modo forced blood-only, após acabar blood o canal pode permanecer ativo até liberar/timeout, mas os pulsos não pagos não produzem o efeito nem debitam recurso; essa observação é relevante a Fire Breath e outros spells canalizados elegíveis.
- Fonte do ajuste: https://github.com/xsharov/VampireSpellsAddon/commit/1ef4e10d4eb2df3a6862a21937359b5e87ea3d5e .

### Boundaries e regressões a testar — nenhum teste realizado
- [ ] Salvar backup de `<world>/serverconfig/vampire_spells_addon-server.toml`; verificar inserção dos novos defaults em mundos existentes e leitura sincronizada de config no dedicated server.
- [ ] Testar `bloodCastingSchools=["blood"]`, `[]`, `["blood","fire","ice"]`, wildcard `*` e ID de escola de addon; rejeitar nomes inválidos via config validator.
- [ ] Para vampiro: mana suficiente, mana insuficiente com sangue suficiente/insuficiente, always-use-blood e redução de cooldown; cada spell aplica **um** débito e **um** cast, sem duplicação em perks/bridges.
- [ ] Confirmar Ray of Siphoning fora do fallback e Devour com mana multiplicada quando a Blood School está desmarcada; testar canais contínuos quando acaba mana/blood.
- [ ] Exercitar `disableHolyHealDamage=false/true` com self-heal, heal em outro vampiro, heal em não vampiro, AoE e Holy utility; assegurar que a opção não desativa reflexão Holy ou bônus de dano em NPC Vampirism.
- [ ] Testar logout, death/respawn, mudança de dimensão e casts concorrentes em multiplayer para evitar correlações stale, débito duplicado e efeitos aplicados a jogador errado.
- [ ] Validar integração com **Vampirism 1.10.13**, **Iron's Spells 3.16.3**, **NeoForge 21.1.250** documentados no último inventário, sem presumir que os ranges declarados equivalem a QA de runtime.

**Conclusão documental:** a **única release 0.0.10** posterior à 0.0.9 no alvo NeoForge 1.21.1 foi descrita a partir de **changelog do arquivo exato**, com complementos de código-fonte do autor separados e identificados. **A pesquisa das alterações publicadas deste intervalo está concluída** e o item #77 pode sair da fila **documental**. A promoção física do JAR 0.0.10, a verificação de seu bytecode e os testes in-game **não** foram realizados; preservar arquivo de dossier, JAR e runtime em 0.0.9.

**Fontes:** https://www.curseforge.com/minecraft/mc-mods/vampirism-irons-spells-compatibility/files/9226160 ; https://github.com/xsharov/VampireSpellsAddon/pull/9 ; commits pinados acima.
