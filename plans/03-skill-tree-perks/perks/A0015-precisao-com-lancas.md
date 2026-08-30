# A0015 — Precisão com Lanças

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE.
- **Pendência específica identificada:** nenhuma nesta auditoria documental.
- **Notion:** https://app.notion.com/p/3c569db9f0db817fabf5fcd853ff038b
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@7f90af76c2b69574378d7f3f1d292e862ccdd6f9`.

## Especificação canônica do Notion

- **Código:** A0015
- **Nome:** Precisão com Lanças
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Lanças
- **Ramo:** Alcance e Controle de Distância
- **Camada:** 2
- **Função na Árvore:** Ramo
- **Tier:** Pequeno
- **Faixa de Poder:** Médio
- **Ranks Máx.:** 3
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** A0013 Treino com Lanças I ≥ 1 rank.
- **Pré-requisitos:** A0013 Treino com Lanças I.
- **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree — pipeline crítico canônico.
- **Efeito:** +3% de chance de crítico com lanças por rank, até +9%.
- **Escalonamento:** até 3 ranks.
- **Gate:** Gateway `epic_spear` acessível + A0013 ≥ 1 rank; gateway da Árvore Exterior.
- **Hook:** chance de crítico em ataques diretos classificados como lança.
- **Fallback:** usar o pipeline crítico canônico apenas em ataques diretos classificados como lança.
- **Regra:** chance específica de lança; uma ação elegível produz no máximo uma resolução crítica canônica e nunca cria segunda rolagem sobre resultado já resolvido.

## Auditoria obrigatória — 9 eixos

1. **Dependências/gates — PASS.** A0013 ≥ 1 rank e gateway são explícitos.
2. **Integração global — PASS.** Compartilha o serviço crítico canônico e evita duplicação entre NeoForge/Epic Fight.
3. **Qualidade/identidade — PASS.** Forma o eixo de precisão necessário aos Notables A0016/A0017.
4. **Topologia — PASS.** Camada 2 bifurca para Controle de Distância e Interceptação.
5. **Especializações — PASS.** Ramo exterior, não classe automática.
6. **PT-BR — PASS.** Nome/efeito de jogador em português.
7. **Notion completo — PASS.** Campos necessários preenchidos.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura modlist — PASS.** Epic Fight classifica a família e a chance adicional passa pelo pipeline crítico compartilhado.

## Contrato técnico esperado

- Bônus: `0,03 × rank(A0015)` de chance crítica para ataques diretos de lança.
- Uma ação possui uma única resolução crítica canônica/root action.
- Resultado crítico provider-native deve ser preservado; bônus do RPG Skill Tree não cria rolagem concorrente.
- Server-authoritative e deduplicado.

## Evidência encontrada na `main`

- `NotionCombatPerkRules.criticalChanceBonus(...)` mapeia `WeaponFamily.SPEAR -> A0015`.
- `A0001A0020CriticalService` centraliza a resolução crítica.
- `A0001A0020EpicFightHooks.onCriticalHit(...)` registra/correlaciona o root crítico NeoForge.
- `onDamagePre(...)` reutiliza essa correlação e passa a chance específica da família.
- A topologia exige A0013 e conecta A0015 a A0016/A0017.

## Pendências técnicas

Nenhuma divergência específica identificada. Novos adapters de lança devem reutilizar o mesmo serviço crítico em vez de implementar rolagem própria.

## Testes obrigatórios

- [x] coeficiente por rank no ruleset;
- [x] serviço crítico dedicado;
- [x] correlação NeoForge ↔ Epic Fight;
- [x] classificação SPEAR no adapter;
- [x] revalidado no CI #2147 para o pipeline/versão exata atuais.

## Auditoria retroativa de integração — projetos próprios + Mobstein 5.4.4 — lote A0011–A0020

- **Resultado:** APROVADA com boundary causal retroativa; coeficientes/gates/providers principais não mudaram.
- **RPG Skill Tree:** mantém o resolver crítico único e a identidade causal/root action; nenhuma bridge pode lançar segunda rolagem.
- **Black Arcana:** `ARCANE_BACKLASH` é terminal e não entra no resolver crítico de A0015, não proca e não concede Mastery.
- **Mobstein 5.4.4:** dano de ally/bodyguard ressuscitado permanece Mobstein-owned e não herda crítico/autoria marcial do dono; ataques diretos do jogador contra entidades Mobstein seguem normais se elegíveis.
- **Volcanoes / Enshrouded:** NÃO DEVE SER INTEGRADO à chance crítica desta perk; hazards/Shroud/Flame não criam critical receipt.
- **Notion:** `Hook`, `Fallback` e `Regra` corrigidos em 2026-08-30; re-fetch confirmou persistência.
- **Chat 2:** revalidar que fontes secundárias/terminal/companions não atravessam o serviço crítico; não criar bridge específica para esses providers.

## Chat 2 — revalidação de implementação — PR #237

- [x] Gate A0013 ≥1 e +3% crítico/rank preservados.
- [x] Resolver crítico canônico continua único por root action.
- [x] Epic Fight é aceito somente em `21.17.3.1` exato.
- [x] `ARCANE_BACKLASH`, companion-owned e demais fontes indiretas ficam neutras antes do critical bonus.
- [x] Resultado crítico provider-native é preservado; nenhuma segunda rolagem foi criada.
- [x] Regressões específicas de provenance + suíte crítica verdes no CI #2147 no mesmo HEAD revalidado.
- [x] NeoForge GameTests, build, JAR e dedicated-server smoke verdes no CI #2147 no mesmo HEAD revalidado.

**Estado Chat 2:** `IMPLEMENTAÇÃO VALIDADA EM CI`; confirmação definitiva ocorre com o merge da PR #237 na `main`.
