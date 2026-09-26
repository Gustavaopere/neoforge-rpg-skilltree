# Platform

## Propriedades do registro

- **Mod:** Platform
- **Arquivo JAR:** Platform-neoforge-1.21.1-1.3.3.jar
- **Versão 1.21.1:** 1.3.3
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/platform
- **Função:** Biblioteca cross-platform baseada em Architectury para abstrair e disponibilizar APIs comuns entre Fabric, Forge, NeoForge e Quilt. Fornece infraestrutura técnica compartilhada, incluindo registries e acesso uniforme a contratos de loader, sem adicionar uma progressão ou conteúdo jogável próprio.
- **Dependências:** NeoForge 1.21.1. Nenhum consumer top-level atual foi comprovado neste passe; mapear manifests/dependency graph antes de qualquer remoção.
- **Compatibilidade/Riscos:** Library Client & Server. Riscos: consumer oculto/não mapeado, API drift das registry classes experimentais, abstração específica de loader, mixin compatibility e false redundancy com outras libraries.
- **Sobreposição:** Cross-platform library baseada em Architectury; não substituível automaticamente por Architectury/Moonlight/Balm ou outra library. Consumer real define necessidade.
- **Observações:** Runtime 1.3.3, file ID 8075942, Release 12/05/2026. Delta publicado da 1.3.3: experimental new registry classes.
- **Procedência:** modlist(1).txt física reconferida em 25/09/2026 + CurseForge oficial Platform 1.3.3 + cruzamento do catálogo por consumers explícitos.
- **Atualização/Status:** REVALIDADO EM 25/09/2026 — Platform 1.3.3/JAR físico reconfirmado; dossiê preservado.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-10

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #445: JAR `Platform-neoforge-1.21.1-1.3.3.jar`, mod id `platform`, runtime `1.3.3`, SHA-1 `4132ad6809008698b7f489a878e5a33a3879bbfd`.

<callout icon="🔎" color="yellow_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `Platform-neoforge-1.21.1-1.3.3.jar`, mod id `platform`, versão `1.3.3`, NeoForge 1.21.1. Platform é uma biblioteca cross-platform baseada em Architectury para abstrair APIs/serviços entre loaders. Nenhum consumer físico atual foi comprovado neste passe, portanto a decisão permanece **Sem decisão**, fail-closed.
</callout>
## 1. Identidade e papel
- **Mod:** Platform.
- **JAR físico:** `Platform-neoforge-1.21.1-1.3.3.jar`.
- **Mod id:** `platform`.
- **Runtime:** `1.3.3`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** BlackGear.
- **Licença:** LGPLv3.
- **Ambiente:** Client & Server.
- **Papel:** biblioteca cross-platform baseada em Architectury para expor contratos comuns entre Fabric, Forge, NeoForge e Quilt.
- **Decisão:** Sem decisão.
## 2. Boundary de abstração
Platform existe para que um consumer use uma camada comum em vez de escrever cada integração de loader separadamente.
Isso não significa que a library substitua Architectury ou qualquer outra API genericamente. Um consumer compilado contra `platform` precisa dos contratos e mod id esperados por ele.
## 3. Registries e serviços comuns
A ficha anterior já identificava registries e acesso uniforme a contratos de loader como parte do escopo da library. A release 1.3.3 adiciona **classes experimentais de registry**.
Por serem explicitamente experimentais, essas classes exigem cautela em consumers:
- assinatura/API pode mudar em releases posteriores;
- não assumir estabilidade binária equivalente à parte madura da library;
- mapear qual consumer usa a API antes de atualizar.
## 4. Lineage 1.3.x
A release 1.3 anterior publicou mudanças em configs, handlers de oxidização/waxable e biome modifiers. A 1.3.3 instalada publica especificamente novas classes experimentais de registry.
Essas notas de lineage servem para orientar regressão, mas não provam que um consumer do pack usa todas essas superfícies.
## 5. Consumer mapping
O cruzamento atual do banco não encontrou outro registro instalado cuja propriedade de dependências/compatibilidade declare Platform de forma explícita.
Conclusão fail-closed:
- library fisicamente presente = confirmado;
- função cross-platform = confirmada;
- consumer atual = **não comprovado neste passe**;
- remoção segura = **não comprovada**;
- decisão = `Sem decisão`, não `Tirar`.
Antes de curadoria, inspecionar manifests dos JARs ou dependency graph físico.
## 6. Client/server e classloading
Como library Client & Server, validar:
- bootstrap em dedicated server;
- serviços específicos do loader resolvidos corretamente;
- ausência de client class em common/server path;
- registries criados uma única vez;
- config/biome hooks não duplicados após reload.
## 7. Mixins físicos
O JAR registra `platform.mixins.json` e `platform-common.mixins.json`.
Isso comprova que Platform possui integração runtime além de ser apenas um conjunto de classes. A presença de mixins aumenta a importância de testar updates contra a versão exata do loader e consumers, sem inferir os targets internos sem source pinado.
## 8. Atualização e ABI
Library cross-platform pode sofrer drift em:
- assinatura de serviço;
- abstrações de registry;
- eventos por loader;
- classes experimentais;
- configs e biome modifiers.
Ao atualizar, o teste decisivo é o consumer real, não apenas o boot isolado da library.
## 9. Sobreposição
Platform, Architectury, Moonlight, Balm e outras libraries podem coexistir porque atendem consumers diferentes. Categoria “Biblioteca” não implica redundância técnica.
Remoção só pode ser decidida depois de descobrir quem declara `platform` ou chama sua API.
## 10. Riscos
1. **Consumer desconhecido:** dependente não mapeado pode quebrar no startup após remoção.
2. **Experimental registry API:** maior risco de drift.
3. **Loader abstraction:** diferença NeoForge/Fabric pode produzir bug só em um target.
4. **Mixin compatibility:** update do loader/MC pode alterar targets.
5. **False redundancy:** outra cross-platform library não substitui Platform automaticamente.
6. **Classloading:** common code pode alcançar classe de lado/loader errado.
## 11. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Platform 1.3.3.
- [ ] Dependency scan identifica todos os JARs que declaram `platform`.
- [ ] Consumers encontrados registram conteúdo sem missing service/class.
- [ ] Registries experimentais, se usados, não duplicam entries.
- [ ] Config load/reload de consumers permanece estável.
- [ ] Biome/world hooks de consumers, se usados, não duplicam após `/reload`.
- [ ] Remoção só é testada em cópia da instância após dependency graph completo.
- [ ] Update futuro é validado por consumer, não só pelo boot da library.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 12. Evidências e limites
- Modlist física: `Platform-neoforge-1.21.1-1.3.3.jar`, mod id/runtime e dois mixin configs.
- CurseForge oficial: project 997634, file ID 8075942, Release NeoForge 1.21.1 de 12/05/2026, Client & Server, LGPLv3.
- Changelog 1.3.3: classes experimentais novas de registry.
- Descrição oficial: cross-platform library baseada em Architectury para acesso uniforme a APIs de loaders.
- Cruzamento do catálogo atual não localizou consumer explícito adicional.
- **Limite:** não foi executado dependency scan de manifests das 587 entradas top-level da modlist física atual, incluindo o modloader, neste passe; por isso não declarar library órfã nem segura para remoção.
