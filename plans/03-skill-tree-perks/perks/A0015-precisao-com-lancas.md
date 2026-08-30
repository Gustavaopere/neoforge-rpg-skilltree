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
- [ ] revalidar quando o pipeline crítico ou a versão do Epic Fight mudar.

## Fechamento Chat 1 V3 — ciclo exato A0011–A0020

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; dependência, chance, gate, hook, fallback e regra permanecem coerentes.
- **Mutação no Notion neste ciclo:** não necessária.
- **Pipeline canônico:** uma ação de lança pode produzir no máximo uma resolução crítica; NeoForge, Epic Fight e qualquer bridge futura precisam correlacionar a mesma root action.
- **Providers de atributos:** Pufferfish/Apothic ou gear com crítico não autorizam uma segunda rolagem MARTIAL; integração futura deve entrar no resolvedor canônico ou permanecer separada quando semanticamente distinta.
- **Fail-closed:** fontes não classificadas inequivocamente como SPEAR não recebem A0015.
- **Resultado:** **APROVADA / FECHADA** no lote A0011–A0020.