# Decisões arquiteturais

## D001 — Runtime é autoridade sobre documentação histórica
Aceita. Specs antigas continuam úteis, mas não provam implementação.

## D002 — Progressão é server-authoritative
Aceita. XP, level, mastery, unlocks, gating e efeitos de gameplay não dependem de confiança no cliente.

## D003 — Conteúdo de árvore é data-driven
Aceita. IDs, requisitos e efeitos permanecem carregáveis/validáveis por dados quando o sistema oferece esse contrato.

## D004 — 512 é o orçamento materializado da Árvore Principal, não sinônimo de 512 efeitos distintos
Aceita. Alguns nós são estruturais ou gateways; efeitos também vivem em packs e handlers.

## D005 — Wiki de jogador vive em `wiki/`, na raiz
Aceita. `wiki/` não contém arquitetura, tarefas de programação ou guia de desenvolvedor.

## D006 — Integração genérica continua genérica
Aceita. Um modificador só é atribuído nominalmente a conteúdo externo quando existe evidência específica dessa relação.

## D007 — Mods opcionais não viram dependência dura por acidente
Aceita. Adapters devem ser isolados e protegidos por presença/contratos apropriados.

## D008 — Create/AE2/Oritech não são anunciados como runtime completo sem adapter comprovado
Aceita. Especializações, gateways ou nomes de perks podem existir sem interceptação completa de máquinas/redes.

## D009 — IDs persistidos são API de save
Aceita. Renomear/remover skill, classe, mastery ou especialização exige compatibilidade ou migração explícita.

## D010 — Um evento de gameplay concede progressão uma vez
Aceita. Bridges devem preferir confirmação semântica real e idempotência.

## D011 — Planos seguem o padrão Volcanoes
Aceita. Cada estágio tem `README.md` + múltiplos arquivos numerados. Trabalho concluído é marcado renomeando `01-x.md` para `✅-01-x.md`.

## D012 — O check pertence ao subplano
Aceita. Não é necessário esperar o estágio inteiro terminar para marcar um arquivo; porém o arquivo só recebe check depois de implementação, validação e integração completas do seu próprio escopo.

## D013 — Cada subplano é executável isoladamente
Aceita. Todo arquivo numerado deve conter objetivo, passos de implementação, testes/validação e critério de aceite, respeitando dependências causais anteriores.

## D014 — O Dicionário Enciclopédico é um único Compêndio Natural
Aceita. Fauna, flora, árvores, cultivos, biomas, estruturas, dimensões, descoberta e dados técnicos pertencem a um catálogo/navegação únicos, não a três mods/telas concorrentes.

## D015 — Registry/runtime vence catálogo manual do Compêndio
Aceita. A modlist serve como snapshot de planejamento e cobertura, mas `ResourceLocation`/registries efetivamente carregados são a autoridade para conteúdo presente. Conteúdo novo recebe fallback automático em vez de desaparecer.

## D016 — Identidade enciclopédica é `kind + ResourceLocation`
Aceita. Nome traduzido, nome de arquivo do JAR e texto editorial não são chaves de save. Renomear um ID persistido exige migração explícita.

## D017 — Todo texto próprio do Compêndio entregue ao jogador é pt-BR
Aceita. O locale canônico de conteúdo próprio é `pt_br`. IDs técnicos permanecem intactos; nomes de mods externos podem receber alias editorial pt-BR quando necessário, sem alterar a identidade técnica.

## D018 — O Compêndio não inventa fatos
Aceita. Dados técnicos, textos editoriais e inferências carregam proveniência/confiança. Informação não comprovada fica ausente, contextual ou marcada como indisponível; lore não é apresentada como mecânica e vice-versa.

## D019 — Sobrevivência é read-only; ferramentas de operador são superfície separada
Aceita. Desativar AI, alterar invulnerabilidade, ownership, crescimento ou outros estados nunca faz parte da página survival comum. Mutações exigem módulo admin, permissão e validação server-side.

## D020 — Biology Dictionary, Field Guide e Wildex são referências, não dependências obrigatórias
Aceita. O Stage 10 reimplementa comportamentos desejados através de APIs próprias. Código, assets ou corpus externos só podem ser reutilizados depois de auditoria explícita de licença/proveniência.

## D021 — Descoberta e recompensas do Compêndio são server-authoritative e idempotentes
Aceita. O cliente pode solicitar inspeção/ação, mas não declarar descoberta como fato confiável nem conceder recompensa.

## D022 — Cobertura do modpack é um artefato verificável
Aceita. Cada entrada relevante deve terminar como `AUTO`, `CURATED`, `ADAPTER`, `IGNORED` com motivo ou `ERROR`. `ERROR > 0` bloqueia o gate final do Stage 10.

## D023 — Dados base da espécie e dados da instância são conceitos distintos
Aceita. Estatística base de um `EntityType` não pode ser confundida com valor efetivo de uma entidade escalada pelo RPG, buffada, equipada ou modificada por outro sistema. A UI deve rotular a origem/contexto.

## D024 — O catálogo do Compêndio publica snapshots atômicos
Aceita. Reload constrói e valida staging completo antes de substituir o snapshot atual. Falha de datapack/provider mantém a última versão válida em vez de expor estado parcial.
