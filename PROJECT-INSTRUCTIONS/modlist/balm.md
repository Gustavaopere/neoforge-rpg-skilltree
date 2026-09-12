# Balm

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8165993ad1c2180f3153
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Balm
- **Arquivo JAR:** `balm-neoforge-1.21.1-21.0.65.jar`
- **Versão 1.21.1:** 21.0.65
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Camada de abstração multiplataforma para eventos, networking, configurações, registries e superfícies de dados usadas por mods consumidores.
- **Dependências:** Biblioteca estrutural; KumaAPI 21.0.8 está embarcado em META-INF/jarjar dentro do Balm e não é mod top-level.
- **Sobreposição:** Biblioteca técnica; não substitui nem é substituída automaticamente por Architectury, Moonlight ou APIs semelhantes.
- **Compatibilidade/Riscos:** Riscos de registro duplicado common/platform, packet sem server authority, attachment/capability órfão, registry stale após reload e classloading client-only. Não substituir por outra abstraction library por similaridade.
- **Observações:** KumaAPI 21.0.8 é dependência embarcada. Changelog 21.0.65 corrige ChunkTrackingEvent na camada Fabric; tracking de chunk é superfície real da API.
- **Procedência:** modlist.txt física atual de 11/09/2026 + JAR/jarjar físico + CurseForge/documentação/changelog oficial Balm 21.0.65 e fontes já auditadas. Reconciliação final: JAR/runtime permanecem exatamente `balm-neoforge-1.21.1-21.0.65.jar` / `21.0.65`; KumaAPI 21.0.8 continua embarcado, não top-level.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/balm
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #64: `balm-neoforge-1.21.1-21.0.65.jar` / `21.0.65` conferidos contra a modlist atual; networking/config/registries/data/events lifecycle e KumaAPI 21.0.8 jarjar preservados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou Balm 21.0.65 e registrou KumaAPI 21.0.8 como biblioteca embarcada em `META-INF/jarjar`, não top-level. Em 09/09/2026, ambos foram revalidados no snapshot físico atual; a necessidade funcional continua dependente dos consumidores reais, sem decisão curatorial inferida.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `balm-neoforge-1.21.1-21.0.65.jar`, mod id `balm`, runtime `21.0.65`. O JAR contém `kuma-api-neoforge-21.0.8+1.21.jar` em `META-INF/jarjar`; KumaAPI é dependência embarcada e **não conta como mod top-level**.

## 1. Papel e autoridade
Balm é uma **camada de abstração multiplataforma** usada por mods para compartilhar código entre loaders. Ela fornece contratos comuns para infraestrutura recorrente, reduzindo código específico de Fabric/NeoForge. Balm não adiciona gameplay próprio e não substitui o provider funcional de cada mod consumidor.

## 2. Superfícies de infraestrutura
A documentação da linha 21.x cobre, entre outras superfícies:
- eventos/interfaces comuns;
- networking;
- configurações;
- registries e registries custom/dynamic;
- capabilities/data attachments quando aplicável à plataforma;
- abstrações de loader e integração com APIs externas.

A ficha trata essas áreas como **API**, não como sistemas de gameplay independentes.

## 3. Networking
Balm pode fornecer camada comum para registro/manuseio de mensagens. Regras operacionais para consumidores:
- servidor continua authority de qualquer state de gameplay;
- payload precisa ser validado antes de mutar estado;
- packet handler não deve ser registrado duas vezes por common + NeoForge bootstrap;
- reconnect/dimension change não deve deixar listeners duplicados.

## 4. Configuração
A abstração de config deve possuir uma única fonte de verdade por consumidor. Não replicar valores em uma config paralela do modpack. Mudanças client-only não podem alterar state server-side sem sync/validação explícitos.

## 5. Registries e dados
Registries custom/dynamic, capabilities e data attachments exigem atenção a lifecycle:
- registrar na fase correta;
- usar IDs estáveis;
- invalidar attachments/capabilities quando owner deixa de existir;
- não cachear holder/registry entry além do lifecycle permitido;
- datapack reload precisa reconstruir referências dinâmicas quando pertinente.

## 6. Eventos
Eventos expostos por Balm abstraem diferenças de loader. Consumidor deve registrar o handler uma vez e preservar semântica da plataforma. O changelog 21.0.65 corrige na camada Fabric um problema de `ChunkTrackingEvent.Start/Stop`; embora o pack use NeoForge, isso confirma que **tracking de chunk é uma superfície real da API** e deve ser tratada com lifecycle correto.

## 7. Dependência embarcada KumaAPI
A modlist mostra `KumaAPI 21.0.8` dentro do JAR Balm. Consequências:
- não criar uma entrada top-level separada;
- não assumir que outro mod pode depender diretamente da cópia interna sem metadata;
- em diagnóstico de classloading, considerar que a classe pode vir do jarjar de Balm.

## 8. Client/server
Balm é infraestrutura cross-side. Cada superfície precisa respeitar side do consumidor:
- networking/config/registries comuns podem existir nos dois lados;
- UI/render de consumidores não devem vazar para dedicated server por meio da abstração;
- data attachment/capability de gameplay deve ser servidor-autoritativo.

## 9. Relação com outras bibliotecas
Architectury, Moonlight e outras abstraction libraries podem coexistir. Não são substitutas automáticas: consumidores compilam contra APIs específicas. Remover Balm por “redundância de biblioteca” pode quebrar dependentes mesmo que outra library ofereça função parecida.

## 10. Riscos
1. Registro duplicado em common + platform layer.
2. Packet sem validação/server authority.
3. Capability/data attachment órfão após death/unload.
4. Registry holder stale após datapack reload.
5. Classloading de código client-only via abstraction layer.
6. Dependência jarjar confundida com top-level.

## 11. Matriz de testes
1. Dedicated server boot com consumidores Balm.
2. Login/relogin e packet registration sem duplicação.
3. Config load/reload e valores server/client coerentes.
4. Chunk tracking/load/unload nos consumidores que usam eventos correspondentes.
5. Datapack reload com registries/dados dinâmicos.
6. Death/respawn/dimension change para attachments/capabilities de consumidores.
7. Ausência de entrada top-level KumaAPI no catálogo.

## 12. Evidência
- modlist física atual: Balm 21.0.65;
- jarjar físico KumaAPI 21.0.8 dentro de Balm;
- CurseForge/documentação oficial Balm 21.x;
- changelog oficial 21.0.65 sobre `ChunkTrackingEvent`.

> 🧱 Exaustividade proporcional: Balm é infraestrutura. A ficha cobre networking, config, registries, dados, eventos, side e dependência embarcada sem atribuir conteúdo jogável à biblioteca.