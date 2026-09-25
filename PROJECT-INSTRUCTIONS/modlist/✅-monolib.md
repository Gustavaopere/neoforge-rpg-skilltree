# MonoLib

## Propriedades do registro

- **Mod:** MonoLib
- **Arquivo JAR:** monolib-neoforge-1.21.1-4.1.0.jar
- **Versão 1.21.1:** 4.1.0
- **Categoria:** Biblioteca
- **Decisão:** Dependência
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/monolib | https://github.com/Mods-For-Lupin/MonoLib
- **Função:** Biblioteca não invasiva de infraestrutura compartilhada para callbacks, commands, registration binding e utilidades; não adiciona gameplay, blocks, items ou entities segundo o autor.
- **Dependências:** NeoForge 1.21.1. Consumer confirmado no pack: Dis-Enchanting Table 5.0.2; enquanto ele permanecer, MonoLib deve ser tratada como dependência operacional.
- **Compatibilidade/Riscos:** Biblioteca Client & Server com mixins common/NeoForge. Riscos: ABI/API drift, loader mismatch, mixin overlap e quebra de consumers. Dis-Enchanting Table 5.0.2 é consumer físico confirmado.
- **Sobreposição:** Não é intercambiável com Architectury/Balm/Bookshelf/outra library genérica sem adaptação dos consumers; APIs são contratos distintos.
- **Observações:** Runtime 4.1.0. Mixins físicos `monolib.mixins.json` e `monolib.neoforge.mixins.json`. Branch source 1.21.1 confirmada; commit byte-equivalente ao JAR não pinado.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial + source oficial Mods-For-Lupin/MonoLib + consumer físico Dis-Enchanting Table.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — MonoLib 4.1.0 continua sendo a release NeoForge 1.21.1 mais recente localizada; decisão Dependência preservada.
- **Histórico da decisão:** 2026-08-26 — classificado como Dependência após confirmação de Dis-Enchanting Table como consumidor instalado.
- **Data da última decisão:** 2026-09-10

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #405: JAR `monolib-neoforge-1.21.1-4.1.0.jar`, mod id `monolib`, runtime `4.1.0`, SHA-1 `1d67964c61012110d47364140df14a5dfb8eabf3`.

<callout icon="🔎" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `monolib-neoforge-1.21.1-4.1.0.jar`, mod id `monolib`, versão `4.1.0`. O projeto oficial define MonoLib como biblioteca não invasiva e afirma explicitamente que não adiciona gameplay, blocks, items ou entities. A branch pública `1.21.1` existe; a necessidade no pack é sustentada pelo consumer físico **Dis-Enchanting Table 5.0.2**.
</callout>
## 1. Identidade e papel
- **Mod:** MonoLib.
- **JAR físico:** `monolib-neoforge-1.21.1-4.1.0.jar`.
- **Mod id:** `monolib`.
- **Runtime:** `4.1.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** jason13official / Mods-For-Lupin.
- **CurseForge project ID:** 968432.
- **Ambiente:** Client & Server.
- **Licença publicada:** Custom License.
- **Decisão:** **Dependência**.
## 2. Escopo declarado
O autor descreve MonoLib como cornerstone/shared library para reduzir repetição de código entre projetos. A descrição oficial cita utilidades como:
- event callbacks;
- commands;
- registration binding;
- outras funções compartilhadas consumidas por mods dependentes.
O projeto enfatiza que **MonoLib não fornece gameplay novo**. Portanto qualquer bloco/item/mecânica observável deve ser atribuída ao consumer, não à biblioteca.
## 3. Ownership
MonoLib é provider de infraestrutura. Consumers mantêm authority sobre:
- conteúdo registrado por eles;
- regras de gameplay;
- recipes/configs específicos;
- persistência de seus próprios sistemas;
- UI e comportamento final.
Ao diagnosticar stacktrace, uma classe MonoLib no caminho não prova que o bug pertence à biblioteca; pode ser uso incorreto ou incompatibilidade do consumer.
## 4. Consumer confirmado: Dis-Enchanting Table
A modlist física contém `disenchanting_table-merged-1.21.1-5.0.2.jar`. A ficha já auditada desse mod registra MonoLib como dependency requerida.
Consequência operacional:
- MonoLib não deve ser removida isoladamente enquanto Dis-Enchanting Table permanecer;
- atualização da biblioteca exige smoke test do consumer;
- substituir por outra biblioteca genérica não funciona sem o consumer ser recompilado/adaptado.
## 5. Multiloader e branch 1.21.1
O projeto é distribuído para Fabric, Forge e NeoForge em jars específicos. O source público possui branch `1.21.1` e documentação de consumo do componente common.
Isso torna importante não misturar artefatos de loader: um jar Fabric/Forge de mesma versão não é substituto direto do JAR NeoForge físico.
## 6. Mixins
A modlist física registra:
- `monolib.mixins.json`;
- `monolib.neoforge.mixins.json`.
Logo a biblioteca não é apenas um conjunto passivo de helper classes; há transformação via mixin na distribuição instalada. Sem pin de source byte-equivalente auditado para cada mixin, esta ficha não atribui alvos/métodos específicos.
## 7. Client/server e networking
CurseForge classifica o projeto como **Client & Server**. Como library, quais módulos realmente executam em cada side dependem dos consumers.
Não há base documental neste lote para afirmar protocolo próprio de rede, SavedData ou capability concreta de 4.1.0. Essas claims ficam de fora até source/consumer específico exigir.
## 8. Compatibilidade e versionamento
Riscos principais:
- consumer compilado contra outra API MonoLib;
- mudança de assinatura/registry helper entre releases;
- mixin overlap com outros mods;
- distribuição errada de loader;
- configuração/initialization order de consumers.
O projeto recomenda usar a versão recente apropriada ao loader/linha do Minecraft. Para este pack, a authority é a build NeoForge 1.21.1 4.1.0 efetivamente instalada.
## 9. Ausência de gameplay próprio
Para controle de redundância:
- não contar MonoLib como “mais um mod de encantamento” só porque Dis-Enchanting Table depende dela;
- não contar seus registries/helpers como conteúdo de gameplay sem consumer;
- não remover por parecer biblioteca duplicada com Architectury/Balm/Bookshelf; APIs são contratos distintos.
## 10. Riscos técnicos
1. **ABI/API drift** entre MonoLib e consumers.
2. **Mixin collision** no common/NeoForge layer.
3. **Loader mismatch** ao substituir jar por build Fabric/Forge.
4. **Consumer attribution:** erro pode surgir em MonoLib mas ter origem no consumer.
5. **Silent dependency break:** remover a biblioteca pode impedir boot do Dis-Enchanting Table.
6. **No-content assumption:** biblioteca sem gameplay ainda é operacionalmente necessária.
## 11. Matriz de testes
- [ ] Dedicated server inicia com MonoLib 4.1.0 + Dis-Enchanting Table 5.0.2.
- [ ] Cliente conecta sem version/registry mismatch.
- [ ] Dis-Enchanting Table registra bloco/menu/recipe sem missing class.
- [ ] Fluxo principal de disenchant executa sem erro de callback/registration.
- [ ] Restart completo mantém estado do consumer conforme seu próprio contrato.
- [ ] `/reload` não quebra registries/recipes do consumer.
- [ ] Testar eventual outro consumer identificado futuramente após update de MonoLib.
- [ ] Confirmar que remover **apenas em ambiente de teste** MonoLib reproduz dependency failure esperada, se necessário para validar ownership.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 12. Evidências e limites
- Modlist física: `monolib-neoforge-1.21.1-4.1.0.jar`, id `monolib`, versão 4.1.0, mixins common + NeoForge.
- CurseForge oficial: MonoLib, project ID 968432, Client & Server, library sem gameplay próprio.
- Source oficial: `Mods-For-Lupin/MonoLib`; branch `1.21.1` confirmada.
- Consumer físico confirmado: Dis-Enchanting Table 5.0.2.
- **Limite:** não foi pinado neste lote um commit byte-equivalente ao JAR NeoForge 1.21.1-4.1.0; métodos/classes internas não são inventados.
