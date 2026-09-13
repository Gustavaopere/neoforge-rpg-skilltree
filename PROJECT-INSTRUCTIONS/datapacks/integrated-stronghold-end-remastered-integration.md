# Integrated Stronghold- End Remastered Integration

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81c7beb3f7b34af77d3a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Data Pack, Addon
- **Arquivo:** `intstrong_endrem-1.0.0-1.18.2-1.19.2.zip`
- **Versão 1.21.1:** 1.0.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `intstrong_endrem-1.0.0-1.18.2-1.19.2.zip` como o ZIP fisicamente capturado no perfil em 08/09/2026. O nome aponta explicitamente para uma distribuição legada e é preservado sem renomear ou substituir automaticamente.
- A modlist física acessível de 08/09/2026 confirma `integrated_stronghold-1.1.4+1.21.1-neoforge.jar`, mod id `integrated_stronghold`, runtime `1.1.4+1.21.1-neoforge`.
- **End Remastered não está presente na modlist física atual.** Logo, a dependência funcional central da integração não está satisfeita e o datapack não pode ser classificado como operacionalmente validado.
- O projeto hoje lista um main file `intstrong-endrem-1.0` para a linha 1.21.1, mas a autoridade de presença é o ZIP realmente capturado. Nenhuma atualização automática foi aplicada.

## Propriedades do banco

- **Mod:** Integrated Stronghold- End Remastered Integration
- **Arquivo JAR:** `intstrong_endrem-1.0.0-1.18.2-1.19.2.zip`
- **Tipo de conteúdo:** Data Pack, Addon
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Visual, Worldgen
- **Função:** Datapack opcional de integração do Integrated Stronghold que substitui o end portal frame vanilla pelo ancient portal frame do End Remastered quando o stack-alvo está presente.
- **Dependências:** Integrated Stronghold 1.1.4+1.21.1-neoforge está presente. End Remastered é o alvo funcional da integração, mas NÃO está presente na modlist física atual; portanto a integração não pode ser considerada operacionalmente validada.
- **Sobreposição:** Pode substituir dados do portal/structure integration do Integrated Stronghold. Sem End Remastered instalado, qualquer referência ao ancient portal frame deve ser tratada como dependência não satisfeita, não como integração ativa.
- **Compatibilidade/Riscos:** RISCO CRÍTICO: End Remastered está ausente. O ZIP físico `intstrong_endrem-1.0.0-1.18.2-1.19.2.zip` é uma distribuição legada capturada no perfil, enquanto o projeto hoje lista `intstrong-endrem-1.0` para 1.21.1. Não assumir funcionamento no stack atual sem validar datapack parsing e referências.
- **Observações:** Arquivo físico preservado exatamente como capturado: `intstrong_endrem-1.0.0-1.18.2-1.19.2.zip`, versão nominal 1.0.0. O arquivo não é substituído automaticamente pelo main file atual `intstrong-endrem-1.0`.
- **Procedência:** CurseForge oficial Integrated Stronghold - End Remastered Integration + captura Resource Packs do perfil em 08/09/2026 + modlist física Integrated Stronghold 1.1.4; ausência de End Remastered confirmada na modlist física atual.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/integrated-stronghold-end-remastered-integration
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — End Remastered Integration 1.0.0, legacy filename físico, Integrated Stronghold 1.1.4, End Remastered ausente, datapack boundary, riscos críticos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Datapack físico confirmado:** `intstrong_endrem-1.0.0-1.18.2-1.19.2.zip`, versão nominal `1.0.0`. Integrated Stronghold `1.1.4+1.21.1-neoforge` está presente; **End Remastered não está presente na modlist física atual**.

## 1. Papel e authority
Integrated Stronghold- End Remastered Integration é o datapack opcional que integra **Integrated Stronghold** a **End Remastered**. O upstream descreve a função atual como substituir o end portal frame vanilla pelo **ancient portal frame** do End Remastered.

## 2. Dependência funcional ausente
Integrated Stronghold está instalado. End Remastered, porém, não aparece na modlist física atual. Portanto a condição funcional principal da integração **não está satisfeita** e o catálogo não registra o datapack como operacionalmente validado.

## 3. Boundary do arquivo físico
O ZIP capturado é `intstrong_endrem-1.0.0-1.18.2-1.19.2.zip`, um nome associado a distribuição legada. O projeto hoje apresenta `intstrong-endrem-1.0` como main file da linha 1.21.1. A autoridade de presença continua sendo o ZIP físico capturado; ele não é substituído nem renomeado automaticamente no catálogo.

## 4. Tipo correto e lifecycle
O próprio projeto identifica o conteúdo como **End Remastered Integration Datapack**. Ele deve ser tratado como Data Pack + Addon, não como simples Resource Pack. Mudanças dependem do lifecycle de dados do mundo e, quando ligadas à estrutura, devem ser verificadas em stronghold/portal novo de QA.

## 5. Sobreposição
Outros datapacks que substituam dados do portal/stronghold podem disputar os mesmos paths. A prioridade de dados define o resultado efetivo, desde que todas as referências existam no stack.

## 6. Riscos críticos
1. End Remastered ausente no runtime atual.
2. ZIP físico legado não corresponder ao schema esperado pela linha atual do Integrated Stronghold.
3. Referência a IDs/blocos ausentes gerar erro de datapack ou integração inerte.
4. Outro datapack substituir dados do portal.
5. Teste em stronghold já gerado mascarar alterações de estrutura.

## 7. Matriz de testes
- [ ] Confirmar parsing do datapack sem erros no log.
- [ ] Confirmar explicitamente presença de End Remastered antes de testar funcionalidade.
- [ ] Com dependência satisfeita, gerar Integrated Stronghold novo de QA.
- [ ] Verificar ancient portal frame no local esperado.
- [ ] Testar reload/restart e prioridade contra outros datapacks.
- [ ] Confirmar que remover o compat não remove Integrated Stronghold.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma que o projeto é um datapack de integração e que sua função é trocar o portal frame vanilla pelo ancient portal frame. A modlist física confirma Integrated Stronghold e **não contém End Remastered**. O catálogo não extrapola funcionamento sem essa dependência.

> Boundary canônico: **Integrated Stronghold controla a estrutura; End Remastered deve fornecer o ancient portal frame; este datapack apenas conecta os dois. Sem End Remastered, a integração fica fail-closed**.
