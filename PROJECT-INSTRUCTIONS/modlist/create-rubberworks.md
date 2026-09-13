# Create: Rubberworks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db817ea41be02cbe659d71
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Create: Rubberworks
- **Arquivo JAR:** `rubberworks-neoforge-1.21.1-1.1.4.jar`
- **Versão 1.21.1:** 1.1.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação
- **Função:** Addon Create de cadeia industrial de borracha: Sapper extrai fluidos de árvores e Compressor transforma fluidos em itens sólidos por recipes próprios.
- **Dependências:** Create 6.0.10 é provider-base presente. KubeJS 2101.7.2-build.374 + KubeJS Create 2101.3.1-build.18 estão presentes e podem alterar recipes. Biomes O' Plenty é compatibilidade upstream, mas não está top-level na modlist atual.
- **Sobreposição:** Sobreposição parcial com TFMG e outras rotas de rubber/resin/plastic. Não remover/unificar sem comparar IDs, tags, recipes, gates e usos concretos.
- **Compatibilidade/Riscos:** Riscos: tree cache stale, cálculo incorreto da penalidade multi-Sapper, dupe/loss de fluido/item, KubeJS recipe drift, Create kinetic drift, material overlap com TFMG e config de natural leaves divergente.
- **Observações:** Release 1.1.4 NeoForge 1.21.1. Delta exato: hint para folhas inválidas/manual placement, config para habilitar/desabilitar exigência de folhas naturais e localização PT-BR. TFMG 1.2.4b-community está presente e deve ser comparado por IDs/recipes, não apenas por tema.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + CurseForge oficial Create: Rubberworks 1.1.4 + changelogs 1.1–1.1.2 usados como lineage de regressão.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-rubberworks
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Create: Rubberworks 1.1.4 reconstruído: Sapper/Compressor, tree validation, multi-Sapper penalty, natural-leaves config, KubeJS recipes, TFMG overlap, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `rubberworks-neoforge-1.21.1-1.1.4.jar`, mod id `rubberworks`, versão `1.1.4`, NeoForge 1.21.1. Create: Rubberworks adiciona uma cadeia Create de extração de fluidos de árvores e compressão em itens sólidos. O pack usa Create 6.0.10 e KubeJS 7.2; Biomes O' Plenty não aparece como JAR top-level no snapshot físico atual.

## 1. Identidade e papel
- **Mod:** Create: Rubberworks.
- **JAR:** `rubberworks-neoforge-1.21.1-1.1.4.jar`.
- **Mod id:** `rubberworks`.
- **Versão instalada:** `1.1.4`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server.
- **Mixin config físico:** `rubberworks.mixins.json`.
- **Papel:** cadeia industrial Create para sapping/extração de fluidos de árvores e compressing de fluidos em itens.

## 2. Máquinas principais
A documentação oficial descreve duas máquinas movidas por rotação:
- **Sapper:** extrai fluidos de árvores válidas conforme recipes/regras de validação;
- **Compressor:** converte fluidos em outputs sólidos por recipes de compressing.

O projeto exemplifica resina → borracha e lava → obsidian como usos do sistema, sem transformar esses exemplos em lista exaustiva de recipes do pack.

## 3. Autoridade e ownership
- **Create 6.0.10:** cinética, rotational power, stress/speed semantics e infraestrutura base de máquinas Create.
- **Rubberworks:** Sapper/Compressor, recipes de sapping/compressing, validação de árvore e conteúdo próprio de rubber/resin.
- **KubeJS:** pode alterar/adicionar/remover recipes expostos, mas não se torna authority das máquinas.
- **Outros mods industriais:** continuam donos de seus próprios rubber/resin/plastic materials.

## 4. Sapper e validação de árvore
O Sapper depende da detecção de uma árvore/contexto válido. A linha 1.1.x recebeu correções e otimizações nessa área, incluindo:
- tooltip para árvore inválida;
- otimização das verificações de validação;
- penalidade de eficiência quando há mais de um Sapper na mesma árvore;
- na 1.1.4, hint quando folhas são inválidas por terem sido colocadas manualmente.

Essa validação evita tratar qualquer combinação arbitrária de logs/leaves como fonte infinita equivalente a uma árvore natural.

## 5. Configuração da 1.1.4
A build instalada adiciona configuração para **habilitar/desabilitar a exigência de folhas naturais**. O comportamento efetivo do pack depende do valor configurado; não foi inferido o valor local apenas pela existência da opção.

Regression gate: mudar a opção e reiniciar/recarregar conforme o mod suporta deve alterar a validação esperada sem deixar cache stale de árvores previamente avaliadas.

## 6. Eficiência com múltiplos Sappers
Desde a 1.1.2, o projeto aplica penalidade de eficiência quando mais de um Sapper usa a mesma árvore. Isso é importante para balanceamento e automação:
- dois Sappers não devem produzir como duas árvores independentes se apontam para a mesma estrutura;
- remover/adicionar Sapper precisa recalcular corretamente a condição;
- chunk unload/reload não deve esquecer associações e gerar produção indevidamente máxima.

## 7. Compressor
O Compressor processa fluidos em itens e utiliza rotational energy do Create. A linha recente já corrigiu problemas de rotação visual e de funcionamento após o primeiro recipe.

Para a build atual, validar atomicidade do processo: input fluid e output item devem liquidar uma única vez, sem dupe/perda em pausa, unload, break ou mudança de velocidade.

## 8. Recipes e KubeJS
A documentação oficial expõe recipe types KubeJS para sapping e compressing. O pack atual contém KubeJS e KubeJS Create, então essa integração é fisicamente relevante.

A linha 1.1 corrigiu recipes de Sapper que não podiam ser removidos via KubeJS. Regression gate: `/reload` deve refletir remoção/adição de recipe sem duplicar handlers e sem exigir que KubeJS assuma ownership da máquina.

## 9. Compatibilidade com Biomes O' Plenty
A 1.1.2 adicionou compatibilidade com Biomes O' Plenty. **Biomes O' Plenty não aparece como JAR top-level na modlist física atual**, portanto essa integração não deve ser tratada como ativa neste snapshot.

A existência de suporte upstream continua útil para futura mudança de modlist, mas não altera o runtime atual sem o provider presente.

## 10. Sobreposição com TFMG e materiais industriais
O pack contém **Create: The Factory Must Grow 1.2.4b-community** e outros sistemas industriais. Há sobreposição conceitual de rubber/resin/plastic chains, mas isso não torna Rubberworks redundante automaticamente.

A auditoria deve diferenciar:
- item/fluido IDs concretos;
- recipes que convertem entre materiais;
- gates de progressão;
- automação/energia exigida;
- usos exclusivos de cada material.

Unificação de tags/recipes deve ser decidida por data/config, não por nome semelhante.

## 11. Client / Server
Recipe validation, fluid consumption, output e progressão funcional são server-authoritative. Render de eixo/máquina, Ponder e tooltips são client-facing.

A linha histórica corrigiu fallback renderer do Compressor usado em Ponder; aparência correta não prova processamento correto, e vice-versa.

## 12. Lifecycle
Validar:
- colocação/removal de Sapper;
- crescimento/alteração de árvore;
- folhas quebradas/recolocadas;
- múltiplos Sappers entrando/saindo da mesma árvore;
- Compressor iniciando/parando por velocidade/stress;
- chunk unload/reload durante processamento;
- world/server restart;
- `/reload` após mudança KubeJS recipe;
- mudança da config de natural leaves.

## 13. Multiplayer e idempotência
Dois jogadores interagindo simultaneamente com a mesma máquina não devem provocar extração/processamento duplicado. O servidor deve liquidar cada operação uma vez e sincronizar tank/output/client animation depois.

KubeJS reload por admin não pode deixar recipe antigo e novo ativos simultaneamente.

## 14. Delta exato da 1.1.4
O changelog oficial da build física adiciona:
- hint quando folhas são inválidas por serem manualmente colocadas;
- config para ativar/desativar a exigência de folhas naturais;
- localização PT-BR.

Esses são os deltas diretamente atribuídos à 1.1.4. Melhorias anteriores da linha 1.1.x foram mantidas somente como regression lineage.

## 15. Riscos técnicos
1. **Tree cache stale:** árvore alterada continua válida/inválida incorretamente.
2. **Multi-sapper accounting:** penalidade não recalcula ou permite produção duplicada.
3. **Fluid/item atomicity:** Compressor duplica/perde input/output em unload/break.
4. **KubeJS recipe drift:** recipe removido continua ativo após reload.
5. **Create kinetic drift:** speed/stress semantics mudam entre versões.
6. **Material duplication:** Rubberworks e TFMG criam rotas paralelas sem unificação desejada.
7. **Config drift:** exigência de natural leaves muda sem restart/reload coerente.
8. **Client/server mismatch:** Ponder/render mostra fluxo diferente do recipe funcional.

## 16. Matriz de testes
- [ ] Dedicated server inicia com Rubberworks 1.1.4 + Create 6.0.10.
- [ ] Sapper reconhece uma árvore válida e rejeita estrutura inválida.
- [ ] Folhas manualmente colocadas seguem a config de natural leaves.
- [ ] Dois Sappers na mesma árvore sofrem a penalidade documentada sem duplicar produção.
- [ ] Remover um Sapper recalcula a situação da árvore.
- [ ] Chunk unload/reload não reseta indevidamente a penalidade/validação.
- [ ] Compressor consome fluido e entrega output uma única vez.
- [ ] Parada/restart de rotação no meio do processo não duplica item/fluido.
- [ ] Recipe KubeJS de sapping pode ser adicionado/removido e `/reload` é idempotente.
- [ ] Recipe KubeJS de compressing permanece coerente servidor/JEI/Ponder quando aplicável.
- [ ] TFMG e Rubberworks não criam loop/dupe acidental por recipes/tags cruzadas.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 17. Evidências e limites
- Modlist física canônica de 10/09/2026: Rubberworks 1.1.4, Create 6.0.10, KubeJS/KubeJS Create e TFMG presentes; Biomes O' Plenty ausente como top-level.
- CurseForge oficial 1.1.4: release NeoForge 1.21.1, config/hint de folhas naturais e PT-BR.
- Changelogs 1.1–1.1.2: KubeJS recipe removal, Plunger/Ponder, multi-Sapper penalty e tree validation optimization tratados como lineage.
- **Limite:** recipes/quantidades/tempos/stress locais não foram inventados; dependem de data/config efetivos do runtime.
