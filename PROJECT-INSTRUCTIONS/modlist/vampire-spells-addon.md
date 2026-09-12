# Vampire Spells Addon

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db81c198efd5e1feaec785
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `vampire_spells_addon-neoforge-1.21.1-0.0.9.jar`, mod id `vampire_spells_addon`, runtime `1.21.1-0.0.9`; Vampirism 1.10.13 e Iron's Spells 3.16.3 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Vampire Spells Addon 0.0.9, Vampirism 1.10.13 e Iron's Spells 3.16.3 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Vampire Spells Addon
- **Arquivo JAR:** `vampire_spells_addon-neoforge-1.21.1-0.0.9.jar`
- **Versão 1.21.1:** 1.21.1-0.0.9
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Categoria:** Magia, Compat, RPG
- **Função:** Bridge Vampirism ↔ Iron's Spells: adapta custo de Blood spells para blood/mana, restaura blood por dano real de Ray of Siphoning/Devour e converte Holy healing/utility em consequências apropriadas para vampiros.
- **Dependências:** NeoForge >=21.1.200 e <21.2; Vampirism >=1.10.7 e <1.11; Iron's Spells >=1.21.1-3.14.3 e <4. Pack atual: NeoForge 21.1.248, Vampirism 1.10.13, Iron's 3.16.3.
- **Sobreposição:** Não cria escola/facção. É tradução entre Blood/Holy do Iron's e fisiologia/blood do Vampirism; qualquer outra bridge nesse mesmo boundary precisa de exactly-once.
- **Compatibilidade/Riscos:** Faixas suportadas atendidas. Riscos: double payment mana+blood, double payout por damage listeners, Holy healing reaplicado por outra bridge, cooldown stacking e correlação stale após cancel/relog/death. Serverconfig real do mundo ainda precisa de QA.
- **Observações:** Mod id `vampire_spells_addon`; runtime `1.21.1-0.0.9`. Vampirism é authority de blood/faction e Iron's de spell/mana/cooldown. Provider-specific fallback deve ser fail-closed.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/GitHub oficiais Vampire Spells Addon 0.0.9. Faixas permanecem atendidas pelo runtime: NeoForge 21.1.248, Vampirism 1.10.13 e Iron's Spells 3.16.3. Serverconfig e runtime QA não foram executados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/vampirism-irons-spells-compatibility
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Vampire Spells Addon 0.0.9 permanece exatamente instalado e continua a release 1.21.1 pertinente; faixas NeoForge/Vampirism/Iron's, pagamento atômico, Blood payout, Holy inversion, riscos e testes preservados.
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

O pack atual satisfaz essas faixas com **NeoForge 21.1.248**, **Vampirism 1.10.13** e **Iron's Spells 3.16.3**.

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
